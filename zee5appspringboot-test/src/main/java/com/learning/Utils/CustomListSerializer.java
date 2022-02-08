package com.learning.Utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.learning.dto.Authenticate;


public class CustomListSerializer extends StdSerializer<Authenticate> {
	@Override
	public void serialize(Authenticate value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		// TODO Auto-generated method stub
		gen.writeObject(value);

	}

	public CustomListSerializer() {
		// TODO Auto-generated constructor stub
		this(null);
	}

	protected CustomListSerializer(Class<Authenticate> t) {
		super(t);
	}

}