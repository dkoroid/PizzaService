package ua.rd.pizzaservice.service.impl;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.rd.pizzaservice.domain.AccumulationCard;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.repository.AccumulationCardRepository;
import ua.rd.pizzaservice.service.AccumulationCardService;
import ua.rd.pizzaservice.service.DiscountService;

@Service
public class AccumulationCardServiceImpl implements AccumulationCardService {
	
	private static final double MAX_TOTAL_PRICE_DISCOUNTED_PERCENT = 0.3d;

	private final AccumulationCardRepository cardRep;
	
	private final DiscountService discountService;
	
	@Autowired
    public AccumulationCardServiceImpl(AccumulationCardRepository cardRep, DiscountService discountService) {
		this.cardRep = cardRep;
		this.discountService = discountService;
    }

    @Override
    public AccumulationCard getAccumulationCardByCustomer(Customer customer) {
        if (!hasAccumulationCard(customer)) {
            throw new NoSuchElementException("Given customer with id "
                    + customer.getId() + " has " + "no accumulation card.");
        }
        AccumulationCard card = cardRep.getCardByOwner(customer);
        return card;
    }

    @Override
    public Boolean hasAccumulationCard(Customer customer) {
    	return cardRep.hasAccumulationCard(customer);
    }

    @Override
    public Boolean assignNewAccumulationCardToCustomer(Customer customer) {
        if (hasAccumulationCard(customer)) {
            return false;
        }

        AccumulationCard card = new AccumulationCard();
        card.setOwner(customer);
        cardRep.create(card);
        return true;
    }

    @Override
    public Boolean activateAccumulationCardForCustomer(Customer customer) {
        if (!hasAccumulationCard(customer)) {
            return false;
        }
        AccumulationCard card = getAccumulationCardByCustomer(customer);
        if (card.getIsActivated()) {
            return false;
        }
        boolean setActivated = true;
        card.setIsActivated(setActivated);
        cardRep.update(card);
        return true;
    }

    @Override
    public Boolean deactivateAccumulationCardForCustomer(Customer customer) {
        if (!hasAccumulationCard(customer)) {
            return false;
        }
        AccumulationCard card = getAccumulationCardByCustomer(customer);
        if (!card.getIsActivated()) {
            return false;
        }
        boolean setActivated = false;
        card.setIsActivated(setActivated);
        cardRep.update(card);
        return true;
    }

	@Override
	public Customer getOwner(AccumulationCard card) {
		return card.getOwner();
	}

	@Override
	public AccumulationCard read(Integer id) {
		AccumulationCard card = cardRep.read(id);
		if (card == null) {
			throw new NoSuchElementException("Given accumulation card with id "
                    + id + " does not exist.");
		}
		return card;
	}

	@Override
	public Double calculateDiscount(AccumulationCard card, Double orderPriceWithDiscounts) {
		double discountAmount = 0d;
		discountAmount = Math.min(calculateCardDiscount(card), calculateMaxDiscountWithGivenTotalPrice(orderPriceWithDiscounts));
		return discountAmount;
	}

	@Override
	public void use(AccumulationCard card, Order order) {
		Double priceWithDiscounts = discountService.calculatePriceWithDiscounts(order);
		Double cardDiscount = calculateDiscount(card, priceWithDiscounts);
		Double newAmount = card.getAmount() + (priceWithDiscounts - cardDiscount);
		card.setAmount(newAmount);
	}
	
	private double calculateCardDiscount(AccumulationCard card) {
		return card.getAmount() * card.getDiscountPercentage();
	}
	
	private double calculateMaxDiscountWithGivenTotalPrice(double orderTotalPrice) {
		return MAX_TOTAL_PRICE_DISCOUNTED_PERCENT * orderTotalPrice;
	}

}
