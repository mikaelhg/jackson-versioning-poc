package io.mikael.poc.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import io.mikael.poc.Version;

import java.util.ArrayList;
import java.util.List;

public class VersioningDeserializerModifier extends BeanDeserializerModifier {

    private final int major, minor, patch;

    private final List<String> ignorableFields = new ArrayList<>();

    public VersioningDeserializerModifier(final int major, final int minor, final int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    private boolean isSupported(final Version version) {
        return this.major >= version.major() && this.minor >= version.minor() && this.patch >= version.patch();
    }

    @Override
    public List<BeanPropertyDefinition> updateProperties(final DeserializationConfig config,
                                                         final BeanDescription beanDesc,
                                                         final List<BeanPropertyDefinition> propDefs)
    {
        final var ret = new ArrayList<BeanPropertyDefinition>();
        for (var reader : propDefs) {
            final var version = reader.getField().getAnnotation(Version.class);
            if (null == version || isSupported(version)) {
                ret.add(reader);
            } else {
                ignorableFields.add(reader.getName());
            }
        }
        return ret;
    }

    @Override
    public BeanDeserializerBuilder updateBuilder(final DeserializationConfig config,
                                                 final BeanDescription beanDesc,
                                                 final BeanDeserializerBuilder builder)
    {
        ignorableFields.forEach(builder::addIgnorable);
        return builder;
    }

}
