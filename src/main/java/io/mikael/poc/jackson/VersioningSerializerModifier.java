package io.mikael.poc.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import io.mikael.poc.Version;

import java.util.ArrayList;
import java.util.List;

public class VersioningSerializerModifier extends BeanSerializerModifier {

    private final int major, minor, patch;

    public VersioningSerializerModifier(final int major, final int minor, final int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    private boolean isSupported(final Version version) {
        return this.major >= version.major() && this.minor >= version.minor() && this.patch >= version.patch();
    }

    @Override
    public List<BeanPropertyWriter> changeProperties(final SerializationConfig config,
                                                     final BeanDescription beanDesc,
                                                     final List<BeanPropertyWriter> beanProperties)
    {
        final List<BeanPropertyWriter> ret = new ArrayList<>();
        for (final var writer : beanProperties) {
            final var version = writer.getAnnotation(Version.class);
            if (null == version || isSupported(version)) {
                ret.add(writer);
            }
        }
        return ret;
    }

}
