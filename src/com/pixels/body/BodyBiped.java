package com.pixels.body;

import java.util.ArrayList;
import java.util.Arrays;

import com.pixels.entity.Entity;

public class BodyBiped extends Body {

	public BodyBiped(Entity e, float w, float h) {
		super(e, w, h, new ArrayList<BodyDirection>(Arrays.asList(
				new BodyDirection(1f, 0.125f),
				new BodyDirection(1f, 0.125f),
				new BodyDirection(1f, 0.125f),
				new BodyDirection(1f, 0.125f),
				new BodyDirection(1f, 0.125f)
				)));
	}

}
