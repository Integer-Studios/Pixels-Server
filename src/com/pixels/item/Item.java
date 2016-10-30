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
		case 2:return poppyHead;
		case 3:return rootFiber;
		case 4:return stone;
		}
		return null;
	}
	
	public static Item cloudberries = new Item(0, "cloudberry");
	public static Item crowberries = new Item(1, "crowberry");
	public static Item poppyHead = new Item(2, "poppy-head");
	public static Item rootFiber = new Item(3, "root-fiber");
	public static Item stone = new Item(4, "stone");
	
	public String name;
	public int id, weight;

}
