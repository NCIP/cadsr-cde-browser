package gov.nih.nci.ncicb.cadsr.objectCart;

import gov.nih.nci.objectCart.domain.CartObject;

import java.util.Comparator;

public class CartObjectItemComparator implements Comparator   {

	private CartObjectItemComparator() {}

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.instance , not before.
	 */
	private static class SingletonHolder { 
		private final static CartObjectItemComparator instance = new CartObjectItemComparator();
	}

	public static CartObjectItemComparator getInstance() {
		return SingletonHolder.instance;
	}
	
	public int compare(Object o1, Object o2) {
		CartObject itemOne = (CartObject)o1;
		CartObject itemTwo = (CartObject)o2;
		String longNameOne = itemOne.getDisplayText();
		String longNameTwo = itemTwo.getDisplayText();
		return longNameOne.compareToIgnoreCase(longNameTwo);
	}

}
