package com.phmc.bmapper.utils;

import com.phmc.bmapper.ChainPropertyDescriptor;
import org.jetbrains.annotations.NotNull;
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
    @NotNull
    Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(final ApplicationContext context);

    /**
     * Create the mapping of all properties between to objects.
     *
     * @param mainClass Main class of application
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
    @NotNull
    Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(final Class<?> mainClass);

    /**
     * <b>Deprecated since 2023-07.</b><br>
     * Use {@link MappingLoader#getMappedProperties(Class)} instead.<br>
     *
     * Create the mapping of all properties between to objects.
     *
     * @param classA    Source class
     * @param classB    Target class
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
    @NotNull
    Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(final Class<?> classA, Class<?> classB);
}
