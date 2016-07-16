package ua.rd.pizzaservice.service.accumulationcard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ua.rd.pizzaservice.domain.AccumulationCard;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.repository.AccumulationCardRepository;

@RunWith(MockitoJUnitRunner.class)
public class SimpleAccumulationCardServiceTest {

	private AccumulationCardService accCardService;

	@Mock
	AccumulationCardRepository cardRep;
	
	private Customer customerWithActivatedCard;
	private Customer customerWithNotActivatedCard;
	private Customer customerWithoutCard;

	private AccumulationCard activatedCard;
	private AccumulationCard notActivatedCard;
	
	@Before
	public void setUp() throws Exception {
		customerWithActivatedCard = new Customer("name1");
		customerWithNotActivatedCard = new Customer("name2");
		customerWithoutCard = new Customer("name3");
		activatedCard = new AccumulationCard();
		activatedCard.setAmount(100d);
		activatedCard.setIsActivated(true);
		activatedCard.setOwner(customerWithActivatedCard);
		notActivatedCard = new AccumulationCard();
		notActivatedCard.setAmount(100d);
		notActivatedCard.setIsActivated(false);
		notActivatedCard.setOwner(customerWithNotActivatedCard);
		when(cardRep.hasAccumulationCard(customerWithActivatedCard)).thenReturn(true);
		when(cardRep.hasAccumulationCard(customerWithNotActivatedCard)).thenReturn(true);
		when(cardRep.hasAccumulationCard(customerWithoutCard)).thenReturn(false);
		when(cardRep.getCardByOwner(customerWithActivatedCard)).thenReturn(activatedCard);
		when(cardRep.getCardByOwner(customerWithNotActivatedCard)).thenReturn(notActivatedCard);
		when(cardRep.update(notActivatedCard)).thenReturn(notActivatedCard);
		when(cardRep.update(activatedCard)).thenReturn(activatedCard);
		when(cardRep.create(any(AccumulationCard.class))).thenReturn(notActivatedCard);
		accCardService = new SimpleAccumulationCardService(cardRep);
	}

	@After
	public void tearDown() throws Exception {
		accCardService = null;
		customerWithActivatedCard = null;
		customerWithNotActivatedCard = null;
		customerWithoutCard = null;
	}

	@Test
	public void testGetAccumulationCardByCustomerWithActivatedCardReturnsCard() {
		System.out.println("test getAccumulationCardByCustomer with activated card returns card");
		AccumulationCard card = accCardService.getAccumulationCardByCustomer(customerWithActivatedCard);
		assertNotNull(card);
	}

	@Test
	public void testGetAccumulationCardByCustomerWithActivatedCardReturnsActivatedCard() {
		System.out.println("test getAccumulationCardByCustomer with activated card returns activated card");
		AccumulationCard card = accCardService.getAccumulationCardByCustomer(customerWithActivatedCard);
		assertTrue(card.getIsActivated());
	}

	@Test
	public void testGetAccumulationCardByCustomerWithNotActivatedCardReturnsCard() {
		System.out.println("test getAccumulationCardByCustomer with not activated card returns card");
		AccumulationCard card = accCardService.getAccumulationCardByCustomer(customerWithNotActivatedCard);
		assertNotNull(card);
	}

	@Test
	public void testGetAccumulationCardByCustomerWithNotActivatedCardReturnsNotActivatedCard() {
		System.out.println("test getAccumulationCardByCustomer with activated card returns card");
		AccumulationCard card = accCardService.getAccumulationCardByCustomer(customerWithNotActivatedCard);
		assertFalse(card.getIsActivated());
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetAccumulationCardByCustomerWithoutCardThrowsNoSuchElementException() {
		System.out.println("test getAccumulationCardByCustomer with activated card returns card");
		accCardService.getAccumulationCardByCustomer(customerWithoutCard);
	}

	@Test
	public void testHasAccumulationCardWithCustomerWithActivatedCardReturnsTrue() {
		System.out.println("test hasAccumulationCard with customer with activated card returns true");
		assertTrue(accCardService.hasAccumulationCard(customerWithActivatedCard));
	}

	@Test
	public void testHasAccumulationCardWithCustomerWithNotActivatedCardReturnsTrue() {
		System.out.println("test hasAccumulationCard with customer with not activated card returns true");
		assertTrue(accCardService.hasAccumulationCard(customerWithNotActivatedCard));
	}

	@Test
	public void testHasAccumulationCardWithCustomerWithoutCardReturnsFalse() {
		System.out.println("test hasAccumulationCard with customer without card returns false");
		assertFalse(accCardService.hasAccumulationCard(customerWithoutCard));
	}

	@Test
	public void testAssignNewAccumulationCardToCustomerWithActivatedCardReturnsFalse() {
		System.out.println("test assignNewAccumulationCardToCustomer with activated card returns false");
		assertFalse(accCardService.assignNewAccumulationCardToCustomer(customerWithActivatedCard));
	}

	@Test
	public void testAssignNewAccumulationCardToCustomerWithActivatedCardDontCreatesNewCard() {
		System.out.println("test assignNewAccumulationCardToCustomer with activated card dont creates new card");
		AccumulationCard cardBefore = accCardService.getAccumulationCardByCustomer(customerWithActivatedCard);
		accCardService.assignNewAccumulationCardToCustomer(customerWithActivatedCard);
		AccumulationCard cardAfter = accCardService.getAccumulationCardByCustomer(customerWithActivatedCard);
		assertTrue(cardBefore == cardAfter);
	}

	@Test
	public void testAssignNewAccumulationCardToCustomerWithNotActivatedCardReturnsFalse() {
		System.out.println("test assignNewAccumulationCardToCustomer with not activated card returns false");
		assertFalse(accCardService.assignNewAccumulationCardToCustomer(customerWithNotActivatedCard));
	}

	@Test
	public void testAssignNewAccumulationCardToCustomerWithNotActivatedCardDontCreatesNewCard() {
		System.out.println("test assignNewAccumulationCardToCustomer with not activated card dont creates new card");
		AccumulationCard cardBefore = accCardService.getAccumulationCardByCustomer(customerWithNotActivatedCard);
		accCardService.assignNewAccumulationCardToCustomer(customerWithNotActivatedCard);
		AccumulationCard cardAfter = accCardService.getAccumulationCardByCustomer(customerWithNotActivatedCard);
		assertTrue(cardBefore == cardAfter);
	}

	@Test
	public void testAssignNewAccumulationCardToCustomerWithoutCardReturnsTrue() {
		System.out.println("test assignNewAccumulationCardToCustomer without card returns true");
//		when(cardRep.create(any(AccumulationCard.class))).thenReturn(notActivatedCard);
		assertTrue(accCardService.assignNewAccumulationCardToCustomer(customerWithoutCard));
	}

	@Test
	public void testAssignNewAccumulationCardToCustomerWithoutCardCreatesCard() {
		System.out.println("test assignNewAccumulationCardToCustomer without card created card");
		assertFalse(accCardService.hasAccumulationCard(customerWithoutCard));
		when(cardRep.hasAccumulationCard(customerWithoutCard)).thenReturn(true);
		when(cardRep.getCardByOwner(customerWithoutCard)).thenReturn(new AccumulationCard());
		accCardService.assignNewAccumulationCardToCustomer(customerWithoutCard);
		assertTrue(accCardService.hasAccumulationCard(customerWithoutCard));
		assertNotNull(accCardService.getAccumulationCardByCustomer(customerWithoutCard));
	}

	@Test
	public void testActivateAccumulationCardForCustomerWithActivatedCardReturnsFalse() {
		System.out.println("test activateAccumulationCardForCustomer with activated card returns false");
		assertFalse(accCardService.activateAccumulationCardForCustomer(customerWithActivatedCard));
	}

	@Test
	public void testActivateAccumulationCardForCustomerWithActivatedCardDontChangesCardState() {
		System.out.println("test activateAccumulationCardForCustomer with activated card dont "
				+ "changes card state");
		AccumulationCard cardBefore = accCardService.getAccumulationCardByCustomer(customerWithActivatedCard);
		boolean before = cardBefore.getIsActivated();
		accCardService.activateAccumulationCardForCustomer(customerWithActivatedCard);
		AccumulationCard cardAfter = accCardService.getAccumulationCardByCustomer(customerWithActivatedCard);
		boolean after = cardAfter.getIsActivated();
		assertEquals(before, after);
	}

	@Test
	public void testActivateAccumulationCardForCustomerWithNotActivatedCardReturnsTrue() {
		System.out.println("test activateAccumulationCardForCustomer with not activated card returns true");
		assertTrue(accCardService.activateAccumulationCardForCustomer(customerWithNotActivatedCard));
	}

	@Test
	public void testActivateAccumulationCardForCustomerWithNotActivatedCardChangesCardState() {
		System.out.println("test activateAccumulationCardForCustomer with not activated card "
				+ "changes card state");
		AccumulationCard cardBefore = accCardService.getAccumulationCardByCustomer(customerWithNotActivatedCard);
		boolean before = cardBefore.getIsActivated();
		when(cardRep.update(notActivatedCard)).thenReturn(notActivatedCard);
		notActivatedCard.setIsActivated(true);
		accCardService.activateAccumulationCardForCustomer(customerWithNotActivatedCard);
		AccumulationCard cardAfter = accCardService.getAccumulationCardByCustomer(customerWithNotActivatedCard);
		boolean after = cardAfter.getIsActivated();
		assertNotEquals(before, after);
	}

	@Test
	public void testActivateAccumulationCardForCustomerWithoutCardReturnsFalse() {
		System.out.println("test activateAccumulationCardForCustomer without card returns false");
		assertFalse(accCardService.activateAccumulationCardForCustomer(customerWithoutCard));
	}

	@Test
	public void testDeactivateAccumulationCardForCustomerWithActivatedCardReturnsTrue() {
		System.out.println("test deactivateAccumulationCardForCustomer with activated card returns true");
//		when(cardRep.update(any(AccumulationCard.class))).thenReturn(activatedCard);
		assertTrue(accCardService.deactivateAccumulationCardForCustomer(customerWithActivatedCard));
	}

	@Test
	public void testDeactivateAccumulationCardForCustomerWithActivatedCardChangesCardState() {
		System.out.println("test deactivateAccumulationCardForCustomer with activated card "
				+ "changes card state");
		AccumulationCard cardBefore = accCardService.getAccumulationCardByCustomer(customerWithActivatedCard);
		boolean before = cardBefore.getIsActivated();
		when(cardRep.update(activatedCard)).thenReturn(activatedCard);
		activatedCard.setIsActivated(false);
		accCardService.deactivateAccumulationCardForCustomer(customerWithActivatedCard);
		AccumulationCard cardAfter = accCardService.getAccumulationCardByCustomer(customerWithActivatedCard);
		boolean after = cardAfter.getIsActivated();
		assertNotEquals(before, after);
	}

	@Test
	public void testDeactivateAccumulationCardForCustomerWithNotActivatedCardReturnsFalse() {
		System.out.println("test deactivateAccumulationCardForCustomer with not activated card returns false");
		assertFalse(accCardService.deactivateAccumulationCardForCustomer(customerWithNotActivatedCard));
	}

	@Test
	public void testDeactivateAccumulationCardForCustomerWithNotActivatedCardDontChangesCardState() {
		System.out.println("test deactivateAccumulationCardForCustomer with not activated card "
				+ "dont changes card state");
		AccumulationCard cardBefore = accCardService.getAccumulationCardByCustomer(customerWithNotActivatedCard);
		boolean before = cardBefore.getIsActivated();
		accCardService.deactivateAccumulationCardForCustomer(customerWithNotActivatedCard);
		when(cardRep.update(notActivatedCard)).thenReturn(notActivatedCard);
		AccumulationCard cardAfter = accCardService.getAccumulationCardByCustomer(customerWithNotActivatedCard);
		boolean after = cardAfter.getIsActivated();
		assertEquals(before, after);
	}

	@Test
	public void testDeactivateAccumulationCardForCustomerWithoutCardReturnsFalse() {
		System.out.println("test deactivateAccumulationCardForCustomer without card returns false");
		assertFalse(accCardService.deactivateAccumulationCardForCustomer(customerWithoutCard));
	}
}
