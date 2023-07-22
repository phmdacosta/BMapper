package com.phmc.bmapper.utils;

import com.phmc.bmapper.ChainPropertyDescriptor;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public interface MappingLoader {

    /**
     * Create the mapping of all properties between to objects.
     *
     * @param context   Application context
     * @return
     * <br>
     * A map with {@link ChainPropertyDescriptor} key and {@link ChainPropertyDescriptor} value,
     * where the key has properties of the target object and the value has properties of the source object.
     *
     * <pre>
     * <code>
     * Example: <br>
     *      Source -> Square{ height, width } <br>
     *      Target -> SquareDTO{ height } <br>
     *      Map -> [ <br>
     *              key: ChainPropertyDescriptor{ SquareDTO::height }, <br>
     *              value: ChainPropertyDescriptor{ Square::height } <br>
     *             ]
     * </code>
     * <pre/>
     */
    Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(final ApplicationContext context);

}
