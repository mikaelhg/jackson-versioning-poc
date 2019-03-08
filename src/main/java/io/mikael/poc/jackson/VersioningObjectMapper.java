package io.mikael.poc.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;

public class VersioningObjectMapper extends ObjectMapper {

    public VersioningObjectMapper(final int major, final int minor, final int patch) {
        super();
        final var serializerModifier = new VersioningSerializerModifier(major, minor, patch);
        final var deserializerModifier = new VersioningDeserializerModifier(major, minor, patch);
        setSerializerFactory(BeanSerializerFactory.instance.withSerializerModifier(serializerModifier));
        this._deserializationContext = new DefaultDeserializationContext.Impl(
                BeanDeserializerFactory.instance.withDeserializerModifier(deserializerModifier));
    }

}
