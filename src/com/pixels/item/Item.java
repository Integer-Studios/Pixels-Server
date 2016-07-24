package com.pixels.item;

public class Item {
	
	public Item(int i, String t) {
		id = i;
		name = t;
		weight = 1;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public Item setWeight(int i) {
		weight = i;
		return this;
	}
	
	public static Item getItemByID(int id) {
		switch (id) {
		case 0:return cloudberries;
		case 1:return crowberries;
		}
		return null;
	}
	
	public static Item cloudberries = new Item(0, "cloudberry");
	public static Item crowberries = new Item(1, "crowberry");
	
	public String name;
	public int id, weight;

}
