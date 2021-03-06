package simulator.checkout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import config.Configuration;
import config.Groceries;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import simulator.grocery.GroceryInterface;
import simulator.shopper.Shopper;

public class NormalLineTest {

  private Shopper bob;
  private Shopper pete;
  private Shopper mete;
  
  /**
   * Creates a couple of shoppers to be used in the test case.
   * @throws Exception - allows method to throw exceptions
   */
  @Before
  public void setUp() throws Exception {
    LinkedList<GroceryInterface> shortList = new LinkedList<GroceryInterface>();
    shortList.add(Groceries.getApple());
    shortList.add(Groceries.getBeef());

    bob = new Shopper(shortList, 0);

    LinkedList<GroceryInterface> whereIsTheBeef = new LinkedList<GroceryInterface>();
    for (int i = 0; i < 15; i++) {
      whereIsTheBeef.add(Groceries.getBeef());
    }

    pete = new Shopper(whereIsTheBeef, 0);

    LinkedList<GroceryInterface> tooMuchBeef = new LinkedList<GroceryInterface>();
    for (int i = 0; i < 100; i++) {
      tooMuchBeef.add(Groceries.getBeef());
    }

    mete = new Shopper(tooMuchBeef, 0);
  }

  @Test (timeout = 100)
  public void test() {
    CheckoutLineInterface col = Configuration.getNormalLine();
    assertTrue(col.canEnterLine(bob));
    col.enqueue(bob);
    assertTrue(col.canEnterLine(pete));
    col.enqueue(pete);
    assertTrue(col.canEnterLine(mete));
    col.enqueue(mete);

    assertEquals(bob, col.dequeue());
    assertEquals(pete, col.dequeue());
    assertEquals(mete, col.dequeue());
  }

  @Test (timeout = 100, expected = NullPointerException.class)
  public void testNullPointerException1() {
    Configuration.getNormalLine().enqueue(null);
  }

  @Test (timeout = 100, expected = NullPointerException.class)
  public void testNullPointerException2() {
    Configuration.getNormalLine().canEnterLine(null);
  }

}
