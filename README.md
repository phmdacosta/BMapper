# Bmapper
Bean mapper

## Version

v1.0.1

### Installing

From Maven:

Add the repository url in your project like this:

```
<dependency>
  <groupId>com.phmc</groupId>
  <artifactId>BMapper</artifactId>
  <version>1.0.1</version>
</dependency>
```

## Initialize

<code>
    // Initializing BMapper <br>
    BMapper bMapper = BMapperBuilder.init(MyApplication.class).build();

    // Mapping classes
    MyDTO mappedDto = bMapper.map(MyModel, MyDTO.class);
</code>

## How to use

Read the [HOW_TO_USE](https://github.com/phmdacosta/cachesmith/blob/master/HOW_TO_USE.md) to find details how to use the library.

## Authors

**Pedro da Costa** - *Initial work*

See also the list of [contributors](https://github.com/phmdacosta/cachesmith/graphs/contributors) who participated in this project.

## License

CacheSmith is licensed under [MIT License](https://github.com/phmdacosta/cachesmith/blob/master/LICENSE)
