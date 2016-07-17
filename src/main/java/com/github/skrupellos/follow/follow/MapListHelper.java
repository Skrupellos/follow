/* This file is part of Follow (https://github.com/Skrupellos/follow).
 * Copyright (c) 2016 Skruppy <skruppy@onmars.eu> and kratl.
 *
 * Follow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Follow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Follow. If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.skrupellos.follow.follow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.NonNull;

public abstract class MapListHelper<KEY, VALUE> {
private final Map<KEY, List<VALUE>> map = new HashMap<>();
	
	
	protected void define(@NonNull KEY key, @NonNull VALUE value) {
		if(map.get(key) == null) {
			// size of 4 for ArrayList should be sufficient in general
			// and does not pollute memory as much as default of 10
			List<VALUE> list = new ArrayList<>(4);
			list.add(value);
			map.put(key, list);
		} else {
			map.get(key).add(value);
		}
	}
	
	
	protected List<VALUE> lookup(@NonNull KEY key) {
		return map.get(key);
	}

	
	protected Collection<List<VALUE>> getValues() {
		return new LinkedList<>(map.values());
	}
}
