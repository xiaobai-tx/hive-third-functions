## Introduction
My udf functions

## Build

```
cd ${project_home}
mvn clean package
```

It will generate hive-third-functions-1.0-SNAPSHOT-shaded.jar in target directory.

## Use

Put these statements into ${HOME}/.hiverc or exec its on hive cli env.

```
add jar ${jar_location_dir}/hive-third-functions-1.0-SNAPSHOT-shaded.jar
create temporary function arrayContains as 'cc.shanruifeng.functions.array.UDFArrayContains';
create temporary function dayOfWeek as 'cc.shanruifeng.functions.date.UDFDayOfWeek';
create temporary function typeOfDay as 'cc.shanruifeng.functions.date.UDFTypeOfDay'; 
create temporary function zodiacCn as 'cc.shanruifeng.functions.date.UDFZodiacSignCn';
create temporary function zodiacEn as 'cc.shanruifeng.functions.date.UDFZodiacSignEn';
create temporary function pinyin as 'cc.shanruifeng.functions.string.UDFChineseToPinYin';
```

You can use these statements on hive cli env get detail of function.
```
hive> describe function zodiacCn;
zodiacCn(date) - from the input date string or separate month and day arguments, returns the sing of the Zodiac.
```

or

```
hive> describe function extended zodiacCn;
zodiacCn(date) - from the input date string or separate month and day arguments, returns the sing of the Zodiac.
Example:
 > select zodiacCn(date_string) from src;
 > select zodiacCn(month, day) from src;
```