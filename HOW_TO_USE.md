# How to use

## Contents

* [Initiate](#initiate)
* [Mapping models](#mapping-models)

## Initiate

Use BMapperBuilder to build BMapper. The builder provides many tools to configure your mapper.

### Annotation mapping

Passing spring context
````
// Initializing BMapper
BMapper bMapper = BMapperBuilder
                .init() // Initialize the builder
                .enableAnnotationMapping(context) // Enables the annotation mapping configuration
                .build(); // Build Bmapper object

// Get mapped class
MyDTO mappedDto = bMapper.map(MyModel, MyDTO.class);
````

Passing project main class
````
// Initializing BMapper
BMapper bMapper = BMapperBuilder
                .init() // Initialize the builder
                .enableAnnotationMapping(MainClass.class) // Enables the annotation mapping configuration
                .build(); // Build Bmapper object

// Get mapped class
MyDTO mappedDto = bMapper.map(MyModel, MyDTO.class);
````

Passing project root package, or package of annotated classes
````
// Initializing BMapper
BMapper bMapper = BMapperBuilder
                .init() // Initialize the builder
                .enableAnnotationMapping("com.bmapper") // Enables the annotation mapping configuration
                .build(); // Build Bmapper object

// Get mapped class
MyDTO mappedDto = bMapper.map(MyModel, MyDTO.class);
````

### XML mapping

````
// Initializing BMapper
BMapper bMapper = BMapperBuilder
                .init() // Initialize the builder
                .enableXmlMapping() // Enables the XML mapping configuration
                .build(); // Build Bmapper object

// Get mapped class
MyDTO mappedDto = bMapper.map(MyModel, MyDTO.class);
````

### Lazy mapping

BMapper gives the possibility to build the mappings manually when necessary, for that it is necessary to configure the lazy mapping. This will tell mapper which mappings will be created later.
````
// Initializing BMapper
BMapper bMapper = BMapperBuilder
                .init() // Initialize the builder
                .lazyMappings() // Do not build mappings automatically
                .enableAnnotationMapping("com.bmapper") // Enables the annotation mapping configuration
                .build(); // Build Bmapper object
````

To load the property mappings manually afterwards, just call the builder method loadMappingProps passing the mapper object itself as a parameter.
````
BMapperBuilder.loadMappingProps(bMapper);
````

## Mapping models

### Annotation mapping

Library gives to developers a mapping type based on annotations, with that they can map their models while they create it.

````
@MappingClass(targetClass = TestModelExt.class)
public class TestViewExtAnnot {

    @MappingField(name = "bool")
    private boolean viewBool;
    
    @MappingField(name = "child")
    private TestChildView childView;
    
    @MappingField(name = "child.name")
    private String childViewName;
    
    @MappingCollection(name = "children", resultElementClass = TestChildView.class)
    private List<TestChildView> viewChildren = new ArrayList<>();
    
    @MappingMap(name = "childrenMap", resultValueClass = TestChildView.class)
    private Map<Integer, TestChildView> viewChildrenMap = new HashMap<>();
    
    @MappingField(name = "strExt")
    private String viewStrExt;
    
    //Getters and setters
}
````

### XML mapping

You can also map your models through XML files.

PS: The name of the file must follow the schema: ClassAName_bmapper.xml

````
MyModel_bmapper.xml

<bmapper xmlns:xsi="http://www.w3.org/2001/XMLSchema">
    <mapping
            class-a="com.phmc.bmapper.test.obj.MyModel"
            class-b="com.phmc.bmapper.test.obj.MyDTO">
        <field>
            <a>name</a>
            <b>viewName</b>
        </field>
        <field>
            <a>bool</a>
            <b>bool</b>
        </field>
        <field>
            <a>child</a>
            <b>childView</b>
        </field>
        <field>
            <a>child.name</a>
            <b>childViewName</b>
        </field>
        <field type="java.util.List">
            <a>children</a>
            <b>childrenView</b>
        </field>
        <field type="java.util.Map">
            <a>childrenMap</a>
            <b>childrenViewMap</b>
        </field>
    </mapping>
</bmapper>
````