package org.salespointframework.order;

import java.util.Optional;

import org.salespointframework.payment.PaymentMethod;
import org.salespointframework.time.Interval;
import org.salespointframework.useraccount.UserAccount;

/**
 * A service to manage {@link Order}s.
 * 
 * @author Thomas Dedek
 * @author Paul Henke
 * @author Oliver Gierke
 */
public interface OrderManager<T extends Order> {

	/**
	 * Saves the given {@link Order} or persists changes to it.
	 * 
	 * @param order the order to be saved, must not be {@literal null}.
	 * @return
	 */
	T save(T order);

	/**
	 * Returns the order identified by an {@link OrderIdentifier}
	 * 
	 * @param orderIdentifier identifier of the {@link Order} to be returned, must not be {@literal null}.
	 * @return the order if the orderIdentifier matches, otherwise {@link Optional#empty()}.
	 */
	Optional<T> get(OrderIdentifier orderIdentifier);

	/**
	 * Checks if this {@link OrderManager} contains an order.
	 * 
	 * @param orderIdentifier the {@link OrderIdentifier} of the {@link Order}, must not be {@literal null}.
	 * @return {@literal true} if the OrderManager contains the order, {@literal false} otherwise.
	 */
	boolean contains(OrderIdentifier orderIdentifier);

	/**
	 * Returns all {@link Order}s having the {@link OrderStatus} {@code status}. If no orders with the specified status
	 * exist, an empty Iterable is returned.
	 * 
	 * @param orderStatus Denoting the {@link OrderStatus} on which the {@link Order}s will be requested.
	 * @return an Iterable containing all {@link Order}s with the specified {@link OrderStatus}
	 */
	Iterable<T> findBy(OrderStatus orderStatus);

	/**
	 * Returns all {@link Order}s in between the {@link Interval}. So every entry with an time stamp <= to and >= from is
	 * returned. If no {@link Order}s within the specified time span exist, an empty {@link Iterable} is returned.
	 * 
	 * @param interval The time interval to find {@link Order}s in, must not be {@literal null}.
	 * @return an {@link Iterable} containing all {@link Order}s in the given {@link Interval}.
	 */
	Iterable<T> findBy(Interval interval);

	/**
	 * Returns all {@link Order}s of the given {@link UserAccount}. If this user has no orders, an empty {@link Iterable}
	 * is returned.
	 * 
	 * @param userAccount Denoting the {@link UserAccount} on which the orders will be requested, must not be
	 *          {@literal null}.
	 * @return an {@link Iterable} containing all orders of the specified user.
	 */
	Iterable<T> findBy(UserAccount userAccount);

	/**
	 * Returns all {@link Order}s from the given {@link UserAccount} in between the dates {@code from} and {@code to},
	 * including from and to. So every entry with an time stamp <= to and >= from is returned. If this user has no
	 * {@link Order}s in this period, an empty {@link Iterable} is returned.
	 * 
	 * @param userAccount The {@link UserAccount} whose {@link Order}s shall be returned, must not be {@literal null}.
	 * @param interval The time interval to find {@link Order}s in, must not be {@literal null}.
	 * @return an {@link Iterable} containing all orders from the specified user in the specified period.
	 */
	Iterable<T> findBy(UserAccount userAccount, Interval interval);

	/**
	 * Tries to complete this order, the {@link OrderStatus} has to be PAID.
	 * 
	 * @param order the order to complete, must not be {@literal null}.
	 * @return an {@link OrderCompletionResult}
	 */
	OrderCompletionResult completeOrder(T order);

	/**
	 * Pays the {@link Order}, {@link OrderStatus} must be OPEN and {@link PaymentMethod} must be set.
	 * 
	 * @param order the order to be payed, must not be {@literal null}.
	 * @return true if the order could be payed
	 */
	boolean payOrder(T order);

	/**
	 * Cancels an {@link Order}, it can only be cancelled is {@link OrderStatus} is OPEN.
	 * 
	 * @param order the order to be canceled, must not be {@literal null}.
	 * @return true if the order could be canceled
	 */
	boolean cancelOrder(T order);
}
