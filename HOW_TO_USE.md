# How to use

## Contents

* [About library](#about-library)
* [Initiate](#initiate)
* [Mapping models](#mapping-models)

## About library


## Initiate

Use BMapperBuilder to build BMapper. The builder provides many tools to configure your mapper.

````
// Initializing BMapper
BMapper bMapper = BMapperBuilder
                .init(MyApplication.class) // Initialize the builder, pass your Main class here
                .enableXmlMapping() // Enables the xml mapping configuration
                .disableAnnotationMapping() // Disable mapping through annotation
                .lazyMappings() // Avoid BMapper to create mappings during project build (use this function if you want to load mappings on another step)
                .build(); // Build Bmapper object

// Get mapped class
MyDTO mappedDto = bMapper.map(MyModel, MyDTO.class);
````

## Mapping models

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