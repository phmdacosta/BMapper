package com.phmc.bmapper.test;

import com.phmc.bmapper.BMapper;
import com.phmc.bmapper.builder.BMapperBuilder;
import com.phmc.bmapper.test.obj.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class BMapperTest {

    protected BMapper bMapper;

    @BeforeEach
    public void setUp() {
        this.bMapper = BMapperBuilder
                .init()
                .enableAnnotationMapping(BMapper.class)
                .build();
    }

    @Test
    public void test_viewMapping_commonFields_success() {
        TestModel model = new TestModel();
        model.setId(1);
        model.setName("Model Name");
        model.setBool(true);

        TestChildModel child = new TestChildModel();
        child.setName("Child Name");
        model.setChild(child);

        TestView view = bMapper.map(model, TestView.class);
        assertEquals(model.getName(), view.getName());
        assertEquals(model.isBool(), view.isBool());
        assertEquals(model.getChild().getName(), view.getChild().getName());
    }

    @Test
    public void test_modelMapping_commonFields_success() {
        TestView view = new TestView();
        view.setName("Model Name");
        view.setBool(true);

        TestChildView child = new TestChildView();
        child.setName("Child Name");
        view.setChild(child);

        TestModel model = bMapper.map(view, TestModel.class);
        assertEquals(model.getName(), view.getName());
        assertEquals(model.isBool(), view.isBool());
        assertEquals(model.getChild().getName(), view.getChild().getName());
    }

    @Test
    public void test_bothMapping_collections_success() {
        //Model
        TestModel model = new TestModel();
        TestChildModel child1 = new TestChildModel();
        TestChildModel child2 = new TestChildModel();
        child1.setName("Child Name 1");
        child2.setName("Child Name 2");
        model.getChildren().add(child1);
        model.getChildren().add(child2);
        model.getChildrenMap().put(1, child1);
        model.getChildrenMap().put(2, child2);

        //View
        TestView view = new TestView();
        TestChildView childView1 = new TestChildView();
        TestChildView childView2 = new TestChildView();
        childView1.setName("Child Name 1");
        childView2.setName("Child Name 2");
        view.getChildren().add(childView1);
        view.getChildren().add(childView2);
        view.getChildrenMap().put(1, childView1);
        view.getChildrenMap().put(2, childView1);

        //Mapping
        TestView mappedView = bMapper.map(model, TestView.class);
        TestModel mappedModel = bMapper.map(view, TestModel.class);

        // Assertions
        assertNotNull(mappedView);
        assertNotNull(mappedView.getChildren());
        assertFalse(mappedView.getChildren().isEmpty());
        assertEquals(model.getChildren().size(), mappedView.getChildren().size());
        assertNotNull(mappedView.getChildrenMap());
        assertFalse(mappedView.getChildrenMap().isEmpty());
        assertEquals(model.getChildrenMap().size(), mappedView.getChildrenMap().size());

        assertNotNull(mappedModel);
        assertNotNull(mappedModel.getChildren());
        assertFalse(mappedModel.getChildren().isEmpty());
        assertEquals(view.getChildren().size(), mappedModel.getChildren().size());
        assertNotNull(mappedModel.getChildrenMap());
        assertFalse(mappedModel.getChildrenMap().isEmpty());
        assertEquals(view.getChildrenMap().size(), mappedModel.getChildrenMap().size());

        for (TestChildView mappedChildView : mappedView.getChildren()) {
            Collection<?> found = model.getChildren().stream().filter(
                    childModel -> childModel.getName().equals(mappedChildView.getName())).collect(Collectors.toList());
            assertFalse(found.isEmpty());
        }

        for (TestChildModel mappedChildModel : mappedModel.getChildren()) {
            Collection<?> found = view.getChildren().stream().filter(
                    _child -> _child.getName().equals(mappedChildModel.getName())).collect(Collectors.toList());
            assertFalse(found.isEmpty());
        }

        for (Map.Entry<Integer, TestChildView> entry : mappedView.getChildrenMap().entrySet()) {
            Map.Entry<Integer, TestChildModel> found = model.getChildrenMap().entrySet().stream()
                    .filter(_entry -> _entry.getValue().getName().equals(entry.getValue().getName()))
                    .findFirst().orElse(null);
            assertNotNull(found);
        }

        for (Map.Entry<Integer, TestChildModel> entry : mappedModel.getChildrenMap().entrySet()) {
            Map.Entry<Integer, TestChildView> found = view.getChildrenMap().entrySet().stream()
                    .filter(_entry -> _entry.getValue().getName().equals(entry.getValue().getName()))
                    .findFirst().orElse(null);
            assertNotNull(found);
        }
    }

    @Test
    public void test_viewMapping_annotation_success() {
        TestModel model = new TestModel();
        model.setId(1);
        model.setName("Model Name");
        model.setBool(true);

        TestChildModel child = new TestChildModel();
        child.setName("Child Name");
        model.setChild(child);

        TestChildModel child1 = new TestChildModel();
        TestChildModel child2 = new TestChildModel();
        child1.setName("Child Name 1");
        child2.setName("Child Name 2");
        model.getChildren().add(child1);
        model.getChildren().add(child2);
        model.getChildrenMap().put(1, child1);
        model.getChildrenMap().put(2, child2);

        TestViewAnnot view = bMapper.map(model, TestViewAnnot.class);
        assertEquals(model.getName(), view.getViewName());
        assertEquals(model.isBool(), view.isBool());
        assertEquals(model.getChild().getName(), view.getChildView().getName());
        assertEquals(model.getChild().getName(), view.getChildViewName());
        for (TestChildView childView : view.getChildrenView()) {
            Collection<?> found = model.getChildren().stream().filter(
                    childModel -> childModel.getName().equals(childView.getName())).collect(Collectors.toList());
            assertFalse(found.isEmpty());
        }
    }

    @Test
    public void test_modelMapping_annotation_success() {
        TestViewAnnot view = new TestViewAnnot();
        view.setViewName("Model Name");
        view.setBool(true);
        view.setChildViewName("Child Name");

        TestChildView child = new TestChildView();
        child.setName("Child Name");
        view.setChildView(child);

        TestChildView child1 = new TestChildView();
        TestChildView child2 = new TestChildView();
        child1.setName("Child Name 1");
        child2.setName("Child Name 2");
        view.getChildrenView().add(child1);
        view.getChildrenView().add(child2);
        view.getChildrenViewMap().put(1, child1);
        view.getChildrenViewMap().put(2, child2);

        TestModel model = bMapper.map(view, TestModel.class);
        assertEquals(model.getName(), view.getViewName());
        assertEquals(model.isBool(), view.isBool());
        assertEquals(model.getChild().getName(), view.getChildView().getName());
        assertEquals(model.getChild().getName(), view.getChildViewName());
        for (TestChildModel childModel : model.getChildren()) {
            Collection<?> found = view.getChildrenView().stream().filter(
                    childView -> childView.getName().equals(childModel.getName())).collect(Collectors.toList());
            assertFalse(found.isEmpty());
        }
    }

    @Test
    public void test_bothMapping_annotation_collections_success() {
        //Model
        TestModel model = new TestModel();
        TestChildModel child1 = new TestChildModel();
        TestChildModel child2 = new TestChildModel();
        child1.setName("Child Name 1");
        child2.setName("Child Name 2");
        model.getChildren().add(child1);
        model.getChildren().add(child2);
        model.getChildrenMap().put(1, child1);
        model.getChildrenMap().put(2, child2);

        //View
        TestViewAnnot view = new TestViewAnnot();
        TestChildView childView1 = new TestChildView();
        TestChildView childView2 = new TestChildView();
        childView1.setName("Child Name 1");
        childView2.setName("Child Name 2");
        view.getChildrenView().add(childView1);
        view.getChildrenView().add(childView2);
        view.getChildrenViewMap().put(1, childView1);
        view.getChildrenViewMap().put(2, childView1);

        //Mapping
        TestViewAnnot mappedView = bMapper.map(model, TestViewAnnot.class);
        TestModel mappedModel = bMapper.map(view, TestModel.class);

        // Assertions
        assertNotNull(mappedView);
        assertNotNull(mappedView.getChildrenView());
        assertFalse(mappedView.getChildrenView().isEmpty());
        assertEquals(model.getChildren().size(), mappedView.getChildrenView().size());
        assertNotNull(mappedView.getChildrenViewMap());
        assertFalse(mappedView.getChildrenViewMap().isEmpty());
        assertEquals(model.getChildrenMap().size(), mappedView.getChildrenViewMap().size());

        assertNotNull(mappedModel);
        assertNotNull(mappedModel.getChildren());
        assertFalse(mappedModel.getChildren().isEmpty());
        assertEquals(view.getChildrenView().size(), mappedModel.getChildren().size());
        assertNotNull(mappedModel.getChildrenMap());
        assertFalse(mappedModel.getChildrenMap().isEmpty());
        assertEquals(view.getChildrenViewMap().size(), mappedModel.getChildrenMap().size());

        for (TestChildView mappedChildView : mappedView.getChildrenView()) {
            Collection<?> found = model.getChildren().stream().filter(
                    childModel -> childModel.getName().equals(mappedChildView.getName())).collect(Collectors.toList());
            assertFalse(found.isEmpty());
        }

        for (TestChildModel mappedChildModel : mappedModel.getChildren()) {
            Collection<?> found = view.getChildrenView().stream().filter(
                    _child -> _child.getName().equals(mappedChildModel.getName())).collect(Collectors.toList());
            assertFalse(found.isEmpty());
        }

        for (Map.Entry<Integer, TestChildView> entry : mappedView.getChildrenViewMap().entrySet()) {
            Map.Entry<Integer, TestChildModel> found = model.getChildrenMap().entrySet().stream()
                    .filter(_entry -> _entry.getValue().getName().equals(entry.getValue().getName()))
                    .findFirst().orElse(null);
            assertNotNull(found);
        }

        for (Map.Entry<Integer, TestChildModel> entry : mappedModel.getChildrenMap().entrySet()) {
            Map.Entry<Integer, TestChildView> found = view.getChildrenViewMap().entrySet().stream()
                    .filter(_entry -> _entry.getValue().getName().equals(entry.getValue().getName()))
                    .findFirst().orElse(null);
            assertNotNull(found);
        }
    }

    @Test
    public void test_bothMapping_sameClasses_success() {
        //Model
        TestModel model = new TestModel();
        model.setId(1);
        model.setName("Model Name");
        model.setBool(true);

        TestChildModel child = new TestChildModel();
        child.setName("Child Name");
        model.setChild(child);

        TestChildModel child1 = new TestChildModel();
        TestChildModel child2 = new TestChildModel();
        child1.setName("Child Name 1");
        child2.setName("Child Name 2");
        model.getChildren().add(child1);
        model.getChildren().add(child2);
        model.getChildrenMap().put(1, child1);
        model.getChildrenMap().put(2, child2);

        //Mapping
        TestModel mapped = bMapper.map(model, TestModel.class);

        //Assertions
        assertEquals(model.getName(), mapped.getName());
        assertEquals(model.isBool(), mapped.isBool());
        assertEquals(model.getChild().getName(), mapped.getChild().getName());
        assertNotNull(mapped.getChildren());
        assertFalse(mapped.getChildren().isEmpty());
        assertEquals(model.getChildren().size(), mapped.getChildren().size());
        assertNotNull(mapped.getChildrenMap());
        assertFalse(mapped.getChildrenMap().isEmpty());
        assertEquals(model.getChildrenMap().size(), mapped.getChildrenMap().size());

        for (TestChildModel mappedChildModel : mapped.getChildren()) {
            Collection<?> found = model.getChildren().stream().filter(
                    _child -> _child.getName().equals(mappedChildModel.getName())).collect(Collectors.toList());
            assertFalse(found.isEmpty());
        }

        for (Map.Entry<Integer, TestChildModel> entry : mapped.getChildrenMap().entrySet()) {
            Map.Entry<Integer, TestChildModel> found = model.getChildrenMap().entrySet().stream()
                    .filter(_entry -> _entry.getValue().getName().equals(entry.getValue().getName()))
                    .findFirst().orElse(null);
            assertNotNull(found);
        }
    }

    @Test
    public void test_viewCollectionMapping_fromList_commonFields_success() {
        final List<TestModel> modelList = generateModelList();

        Collection<TestView> viewCol = bMapper.map(modelList, TestView.class);

        int countEquals = 0;
        for (TestView view : viewCol) {
            for (TestModel model : modelList) {
                if (view.getName().equals(model.getName())
                        && view.isBool() == model.isBool()
                        && view.getChild().getName().equals(model.getChild().getName())) {
                    countEquals++;
                }
            }
        }

        assertEquals(countEquals, viewCol.size());
    }

    @Test
    public void test_viewCollectionMapping_fromSet_commonFields_success() {
        final Set<TestModel> modelSet = new HashSet<>(generateModelList());

        Collection<TestView> viewCol = bMapper.map(modelSet, TestView.class);

        int countEquals = 0;
        for (TestView view : viewCol) {
            for (TestModel model : modelSet) {
                if (view.getName().equals(model.getName())
                        && view.isBool() == model.isBool()
                        && view.getChild().getName().equals(model.getChild().getName())) {
                    countEquals++;
                }
            }
        }

        assertEquals(countEquals, viewCol.size());
    }

    @Test
    public void test_modelCollectionMapping_fromList_commonFields_success() {
        final List<TestView> viewList = generateViewList();

        Collection<TestModel> viewCol = bMapper.map(viewList, TestModel.class);

        int countEquals = 0;
        for (TestModel model : viewCol) {
            for (TestView view : viewList) {
                if (model.getName().equals(view.getName())
                        && model.isBool() == view.isBool()
                        && model.getChild().getName().equals(view.getChild().getName())) {
                    countEquals++;
                }
            }
        }

        assertEquals(countEquals, viewCol.size());
    }

    @Test
    public void test_modelCollectionMapping_fromSet_commonFields_success() {
        final Set<TestView> viewSet = new HashSet<>(generateViewList());

        Collection<TestModel> modelCol = bMapper.map(viewSet, TestModel.class);

        int countEquals = 0;
        for (TestModel model : modelCol) {
            for (TestView view : viewSet) {
                if (model.getName().equals(view.getName())
                        && model.isBool() == view.isBool()
                        && model.getChild().getName().equals(view.getChild().getName())) {
                    countEquals++;
                }
            }
        }

        assertEquals(countEquals, modelCol.size());
    }

    @Test
    public void test_viewMappingWithSuper_commonFields_success() {
        TestModelExt model = new TestModelExt();
        model.setId(1);
        model.setName("Model Name");
        model.setBool(true);
        model.setStrExt("Field of extension class");

        TestChildModel child = new TestChildModel();
        child.setName("Child Name");
        model.setChild(child);

        TestViewExt view = bMapper.map(model, TestViewExt.class);
        assertEquals(model.getName(), view.getName());
        assertEquals(model.isBool(), view.isBool());
        assertEquals(model.getChild().getName(), view.getChild().getName());
        assertEquals(model.getStrExt(), view.getStrExt());
    }

    @Test
    public void test_viewMappingWithSuper_annotation_success() {
        TestModelExt model = new TestModelExt();
        model.setId(1);
        model.setName("Model Name");
        model.setBool(true);
        model.setStrExt("Field of extension class");

        TestChildModel child = new TestChildModel();
        child.setName("Child Name");
        model.setChild(child);

        TestChildModel child1 = new TestChildModel();
        TestChildModel child2 = new TestChildModel();
        child1.setName("Child Name 1");
        child2.setName("Child Name 2");
        model.getChildren().add(child1);
        model.getChildren().add(child2);
        model.getChildrenMap().put(1, child1);
        model.getChildrenMap().put(2, child2);

        TestViewExtAnnot view = bMapper.map(model, TestViewExtAnnot.class);
        assertEquals(model.getStrExt(), view.getViewStrExt());
        assertEquals(model.isBool(), view.isViewBool());
        assertEquals(model.getChild().getName(), view.getChildView().getName());
        assertEquals(model.getChild().getName(), view.getChildViewName());

        assertNotNull(view.getViewChildren());
        assertFalse(view.getViewChildren().isEmpty());
        assertEquals(model.getChildren().size(), view.getViewChildren().size());

        assertNotNull(view.getViewChildrenMap());
        assertFalse(view.getViewChildrenMap().isEmpty());
        assertEquals(model.getChildrenMap().size(), view.getViewChildrenMap().size());

        for (TestChildView childView : view.getViewChildren()) {
            Collection<?> found = model.getChildren().stream().filter(
                    childModel -> childModel.getName().equals(childView.getName())).collect(Collectors.toList());
            assertFalse(found.isEmpty());
        }

        for (Map.Entry<Integer, TestChildView> entry : view.getViewChildrenMap().entrySet()) {
            Map<Integer, ?> foundEntryModel = model.getChildrenMap().entrySet().stream().filter(modelEntry ->
                            modelEntry.getKey().equals(entry.getKey()) && modelEntry.getValue().getName().equals(entry.getValue().getName()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            assertFalse(foundEntryModel.isEmpty());
        }
    }

    private List<TestModel> generateModelList() {
        final TestModel model1 = new TestModel();
        model1.setId(1);
        model1.setName("Model 1 Name");
        model1.setBool(true);

        final TestChildModel child1 = new TestChildModel();
        child1.setName("Child 1 Name");
        model1.setChild(child1);

        final TestModel model2 = new TestModel();
        model2.setId(1);
        model2.setName("Model 2 Name");
        model2.setBool(true);

        final TestChildModel child2 = new TestChildModel();
        child2.setName("Child 2 Name");
        model2.setChild(child2);

        List<TestModel> modelList = new ArrayList<>();
        modelList.add(model1);
        modelList.add(model2);

        return modelList;
    }

    private List<TestView> generateViewList() {
        TestView view1 = new TestView();
        view1.setName("Model 1 Name");
        view1.setBool(true);

        TestChildView child1 = new TestChildView();
        child1.setName("Child 1 Name");
        view1.setChild(child1);

        TestView view2 = new TestView();
        view2.setName("Model 2 Name");
        view2.setBool(true);

        TestChildView child2 = new TestChildView();
        child2.setName("Child 2 Name");
        view2.setChild(child2);

        List<TestView> viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);

        return viewList;
    }
}
