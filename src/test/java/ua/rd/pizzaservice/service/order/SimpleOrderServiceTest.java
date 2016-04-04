package ua.rd.pizzaservice.service.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ua.rd.pizzaservice.domain.accumulationcard.AccumulationCard;
import ua.rd.pizzaservice.domain.customer.Customer;
import ua.rd.pizzaservice.domain.order.Order;
import ua.rd.pizzaservice.domain.order.OrderState;
import ua.rd.pizzaservice.domain.pizza.Pizza;


public class SimpleOrderServiceTest {

	Customer customer;
	OrderService orderService;
	
	@Before
	public void setUpCustomer() {
		String name = "";
		customer = new Customer(name);
	}
	
	@Before
	public void setUpOrderService() {
		orderService = new SimpleOrderService();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPlaceNewOrderWithTooMuchPizzasThrowsIllegalArgumentException() {
		System.out.println("test placeNewOrder with too much pizzas throws IllegalArgumentException");
		int length = 11;
		Integer[] arr = new Integer[length];
		orderService.placeNewOrder(customer, arr);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPlaceNewOrderWithZeroPizzasThrowsIllegalArgumentException() {
		System.out.println("test placeNewOrder with zero pizzas throws IllegalArgumentException");
		int length = 0;
		Integer[] arr = new Integer[length];
		orderService.placeNewOrder(customer, arr);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPlaceNewOrderWithNullCustomerThrowsIllegalArgumentException() {
		System.out.println("test placeNewOrder with null customer throws IllegalArgumentException");
		int pizzaCount = 5;
		Integer[] arr = new Integer[pizzaCount];
		Customer nullCustomer = null;
		orderService.placeNewOrder(nullCustomer, arr);
	}
	
	@Test
	public void testPlaceNewOrderWithAppropriatePizzasCount() {
		System.out.println("test placeNewOrder with appropriate pizzas count");
		Integer[] pizzasID = new Integer[]{1, 2, 3, 3, 2, 1};
		int expectedOrderSize = pizzasID.length;
		Order newOrder = orderService.placeNewOrder(customer, pizzasID);
		int orderSize = newOrder.getPizzas().size();
		assertEquals(expectedOrderSize, orderSize);
	}

	@Test
	public void testCanChangeOrderWithNewStateReturnsTrue() {
		System.out.println("test canChange order with NEW state returns true");
		OrderState state = OrderState.NEW;
		Order order = new Order();
		order.setState(state);
		assertTrue(orderService.canChange(order));
	}
	
	@Test
	public void testCanChangeOrderWithInProgressStateReturnsFalse() {
		System.out.println("test canChange order with IN_PROGRESS state returns false");
		OrderState state = OrderState.IN_PROGRESS;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.canChange(order));
	}
	
	@Test
	public void testCanChangeOrderWithDoneStateReturnsFalse() {
		System.out.println("test canChange order with DONE state returns false");
		OrderState state = OrderState.DONE;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.canChange(order));
	}
	
	@Test
	public void testCanChangeOrderWithCancelledStateReturnsFalse() {
		System.out.println("test canChange order with CANCELLED state returns false");
		OrderState state = OrderState.CANCELLED;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.canChange(order));
	}
	
	@Test
	public void testChangeOrderWithNewStateAndAppropriatePizzaCountReturnsTrue() {
		System.out.println("test changeOrder with NEW state and appropriate pizza count "
				+ "returns true");
		int pizzaCount = 5;
		Integer[] arr = new Integer[pizzaCount];
		OrderState state = OrderState.NEW;
		Order order = new Order();
		order.setState(state);
		assertTrue(orderService.changeOrder(order, arr));
	}
	
	@Test
	public void testChangeOrderWithInProgressStateAndAppropriatePizzaCountReturnsFalse() {
		System.out.println("test changeOrder with IN_PROGRESS state and appropriate "
				+ "pizza count returns false");
		int pizzaCount = 5;
		Integer[] arr = new Integer[pizzaCount];
		OrderState state = OrderState.IN_PROGRESS;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.changeOrder(order, arr));
	}
	
	@Test
	public void testChangeOrderWithDoneStateAndAppropriatePizzaCountReturnsFalse() {
		System.out.println("test changeOrder with DONE state and appropriate "
				+ "pizza count returns false");
		int pizzaCount = 5;
		Integer[] arr = new Integer[pizzaCount];
		OrderState state = OrderState.DONE;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.changeOrder(order, arr));
	}
	
	@Test
	public void testChangeOrderWithCancelledStateAndAppropriatePizzaCountReturnsFalse() {
		System.out.println("test changeOrder with CANCELLED state and appropriate "
				+ "pizza count returns false");
		int pizzaCount = 5;
		Integer[] arr = new Integer[pizzaCount];
		OrderState state = OrderState.CANCELLED;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.changeOrder(order, arr));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testChangeOrderWithNewStateAndNotAppropriatePizzaCountThrowsIllegalArgumentException() {
		System.out.println("test changeOrder with NEW state and not appropriate pizza count "
				+ "throws IllegalArgumentException");
		int pizzaCount = 11;
		Integer[] arr = new Integer[pizzaCount];
		OrderState state = OrderState.NEW;
		Order order = new Order();
		order.setState(state);
		orderService.changeOrder(order, arr);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testChangeOrderWithInProgressStateAndNotAppropriatePizzaCountThrowsIllegalArgumentException() {
		System.out.println("test changeOrder with IN_PROGRESS state and not appropriate "
				+ "pizza count throws IllegalArgumentException");
		int pizzaCount = 11;
		Integer[] arr = new Integer[pizzaCount];
		OrderState state = OrderState.IN_PROGRESS;
		Order order = new Order();
		order.setState(state);
		orderService.changeOrder(order, arr);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testChangeOrderWithDoneStateNotAppropriatePizzaCountThrowsIllegalArgumentException() {
		System.out.println("test changeOrder with DONE state and not appropriate "
				+ "pizza count throws IllegalArgumentException");
		int pizzaCount = 11;
		Integer[] arr = new Integer[pizzaCount];
		OrderState state = OrderState.DONE;
		Order order = new Order();
		order.setState(state);
		orderService.changeOrder(order, arr);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testChangeOrderWithCancelledStateAndNotAppropriatePizzaCountThrowsIllegalArgumentException() {
		System.out.println("test changeOrder with CANCELLED state and not appropriate "
				+ "pizza count throws IllegalArgumentException");
		int pizzaCount = 11;
		Integer[] arr = new Integer[pizzaCount];
		OrderState state = OrderState.CANCELLED;
		Order order = new Order();
		order.setState(state);
		orderService.changeOrder(order, arr);
	}
	
	@Test
	public void testProcessOrderWithNewStateReturnsTrue() {
		System.out.println("test processOrder with NEW state returns true");
		OrderState state = OrderState.NEW;
		Order order = new Order();
		order.setState(state);
		assertTrue(orderService.processOrder(order));
	}
	
	@Test
	public void testProcessOrderWithNewStateChangesStateToInProgress() {
		System.out.println("test processOrder with NEW state changes state to IN_PROGRESS");
		OrderState stateFrom = OrderState.NEW;
		OrderState stateTo = OrderState.IN_PROGRESS;
		Order order = new Order();
		order.setState(stateFrom);
		orderService.processOrder(order);
		assertEquals(stateTo, order.getState());
	}
	
	@Test
	public void testProcessOrderWithInProgressStateReturnsFalse() {
		System.out.println("test processOrder with IN_PROGRESS state returns false");
		OrderState state = OrderState.IN_PROGRESS;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.processOrder(order));
	}
	
	@Test
	public void testProcessOrderWithInProgressStateDontChangesState() {
		System.out.println("test processOrder with IN_PROGRESS state dont changes state");
		OrderState stateFrom = OrderState.IN_PROGRESS;
		OrderState stateTo = OrderState.IN_PROGRESS;
		Order order = new Order();
		order.setState(stateFrom);
		orderService.processOrder(order);
		assertEquals(stateTo, order.getState());
	}
	
	@Test
	public void testProcessOrderWithDoneStateReturnsFalse() {
		System.out.println("test processOrder with DONE state returns false");
		OrderState state = OrderState.DONE;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.processOrder(order));
	}
	
	@Test
	public void testProcessOrderWithDoneStateDontChangesState() {
		System.out.println("test processOrder with DONE state dont changes state");
		OrderState stateFrom = OrderState.DONE;
		OrderState stateTo = OrderState.DONE;
		Order order = new Order();
		order.setState(stateFrom);
		orderService.processOrder(order);
		assertEquals(stateTo, order.getState());
	}
	
	@Test
	public void testProcessOrderWithCancelledStateReturnsFalse() {
		System.out.println("test processOrder with CANCELLED state returns false");
		OrderState state = OrderState.CANCELLED;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.processOrder(order));
	}
	
	@Test
	public void testProcessOrderWithCancelledStateDontChangesState() {
		System.out.println("test processOrder with CANCELLED state dont changes state");
		OrderState stateFrom = OrderState.CANCELLED;
		OrderState stateTo = OrderState.CANCELLED;
		Order order = new Order();
		order.setState(stateFrom);
		orderService.processOrder(order);
		assertEquals(stateTo, order.getState());
	}
	
	@Test
	public void testCancelOrderWithNewStateReturnsTrue() {
		System.out.println("test cancelOrder with NEW state returns true");
		OrderState state = OrderState.NEW;
		Order order = new Order();
		order.setState(state);
		assertTrue(orderService.cancelOrder(order));
	}
	
	@Test
	public void testCancelOrderWithNewStateChangesStateToCancelled() {
		System.out.println("test cancelOrder with NEW state changes state to cancelled");
		OrderState stateFrom = OrderState.NEW;
		OrderState stateTo = OrderState.CANCELLED;
		Order order = new Order();
		order.setState(stateFrom);
		orderService.cancelOrder(order);
		assertEquals(stateTo, order.getState());
	}
	
	@Test
	public void testCancelOrderWithInProgressStateReturnsTrue() {
		System.out.println("test cancelOrder with IN_PROGRESS state returns true");
		OrderState state = OrderState.IN_PROGRESS;
		Order order = new Order();
		order.setState(state);
		assertTrue(orderService.cancelOrder(order));
	}
	
	@Test
	public void testCancelOrderWithInProgressStateChangesStateToCancelled() {
		System.out.println("test cancelOrder with IN_PROGRESS state changes state to cancelled");
		OrderState stateFrom = OrderState.IN_PROGRESS;
		OrderState stateTo = OrderState.CANCELLED;
		Order order = new Order();
		order.setState(stateFrom);
		orderService.cancelOrder(order);
		assertEquals(stateTo, order.getState());
	}
	
	@Test
	public void testCancelOrderWithDoneStateReturnsFalse() {
		System.out.println("test cancelOrder with DONE state returns false");
		OrderState state = OrderState.DONE;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.cancelOrder(order));
	}
	
	@Test
	public void testCancelOrderWithDoneStateDontChangesState() {
		System.out.println("test cancelOrder with DONE state dont changes state");
		OrderState stateFrom = OrderState.DONE;
		OrderState stateTo = OrderState.DONE;
		Order order = new Order();
		order.setState(stateFrom);
		orderService.cancelOrder(order);
		assertEquals(stateTo, order.getState());
	}
	
	@Test
	public void testCancelOrderWithCancelledStateReturnsFalse() {
		System.out.println("test cancelOrder with CANCELLED state returns false");
		OrderState state = OrderState.CANCELLED;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.cancelOrder(order));
	}
	
	@Test
	public void testCancelOrderWithCancelledStateDontChangesState() {
		System.out.println("test cancelOrder with CANCELLED state dont changes state");
		OrderState stateFrom = OrderState.CANCELLED;
		OrderState stateTo = OrderState.CANCELLED;
		Order order = new Order();
		order.setState(stateFrom);
		orderService.cancelOrder(order);
		assertEquals(stateTo, order.getState());
	}
	
	@Test
	public void testDoneOrderWithNewStateReturnsFalse() {
		System.out.println("test doneOrder with NEW state returns false");
		OrderState state = OrderState.NEW;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.doneOrder(order));
	}
	
	@Test
	public void testDoneOrderWithNewStateDontChangesState() {
		System.out.println("test doneOrder with NEW state dont changes state");
		OrderState stateFrom = OrderState.NEW;
		OrderState stateTo = OrderState.NEW;
		Order order = new Order();
		order.setState(stateFrom);
		orderService.doneOrder(order);
		assertEquals(stateTo, order.getState());
	}
	
	@Test
	public void testDoneOrderWithInProgressStateReturnsTrue() {
		System.out.println("test doneOrder with IN_PROGRESS state returns true");
		OrderState state = OrderState.IN_PROGRESS;
		Order order = new Order();
		order.setCustomer(customer);
		order.setState(state);
		assertTrue(orderService.doneOrder(order));
	}
	
	@Test
	public void testDoneOrderWithInProgressStateChangesStateToDone() {
		System.out.println("test doneOrder with IN_PROGRESS state changes state to DONE");
		OrderState stateFrom = OrderState.IN_PROGRESS;
		OrderState stateTo = OrderState.DONE;
		Order order = new Order();
		order.setState(stateFrom);
		order.setCustomer(customer);
		orderService.doneOrder(order);
		assertEquals(stateTo, order.getState());
	}
	
	@Test
	public void testDoneOrderWithDoneStateReturnsFalse() {
		System.out.println("test doneOrder with DONE state returns false");
		OrderState state = OrderState.DONE;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.doneOrder(order));
	}
	
	@Test
	public void testDoneOrderWithDoneStateDontChangesState() {
		System.out.println("test doneOrder with DONE state dont changes state");
		OrderState stateFrom = OrderState.DONE;
		OrderState stateTo = OrderState.DONE;
		Order order = new Order();
		order.setState(stateFrom);
		orderService.doneOrder(order);
		assertEquals(stateTo, order.getState());
	}
	
	@Test
	public void testDoneOrderWithCancelledStateReturnsFalse() {
		System.out.println("test doneOrder with CANCELLED state returns false");
		OrderState state = OrderState.CANCELLED;
		Order order = new Order();
		order.setState(state);
		assertFalse(orderService.doneOrder(order));
	}
	
	@Test
	public void testDoneOrderWithCancelledStateDontChangesState() {
		System.out.println("test doneOrder with CANCELLED state dont changes state");
		OrderState stateFrom = OrderState.CANCELLED;
		OrderState stateTo = OrderState.CANCELLED;
		Order order = new Order();
		order.setState(stateFrom);
		orderService.doneOrder(order);
		assertEquals(stateTo, order.getState());
	}
	
	@Mock
	Order order;
	
	@Mock
	List<Pizza> pizzaList;
	
	@Mock
	Pizza pizzaOne;
	
	@Mock
	Pizza pizzaTwo;
	
	@Mock
	Pizza pizzaThree;
	
	@Mock
	Pizza pizzaFour;
	
	@Test
	public void testGetFinalPriceOfOrderWithoutDiscountsAndWithoutAccumulationCard() {
		System.out.println("test getFinalPrice of order without discounts and "
				+ "without accumulation card");
		Order order = new Order();
		AccumulationCard card = new AccumulationCard(customer);
		Boolean isCardActivated = false;
		card.setIsActivated(isCardActivated);
		customer.setAccumulationCard(card);
		order.setCustomer(customer);
		when(order.getPizzas()).thenReturn(pizzaList);
		
		orderService.getFinalPrice(order);
	}
	
	private List<Pizza> getMockedPizzas() {
		@SuppressWarnings("serial")
		List<Pizza> mockedPizzasList = new ArrayList<Pizza>() {
			{
				add(pizzaOne);
				add(pizzaTwo);
				add(pizzaThree);
				add(pizzaFour);
			}
		};
		return mockedPizzasList;
	}
	
	private List<Pizza> getFirstThreeMockedPizzas() {
		@SuppressWarnings("serial")
		List<Pizza> mockedPizzasList = new ArrayList<Pizza>() {
			{
				add(pizzaOne);
				add(pizzaTwo);
				add(pizzaThree);
			}
		};
		return mockedPizzasList;
	}
}
