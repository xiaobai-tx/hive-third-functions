#hive-third-functions [![Build Status](https://travis-ci.org/aaronshan/hive-third-functions.svg?branch=master)](https://travis-ci.org/aaronshan/hive-third-functions)

## Introduction

Some useful custom hive udf functions, especial json functions.

> Note:
> hive-third-functions support hive-0.11.0 or higher.

## Build

### 1. install dependency

Now, jdo2-api-2.3-ec.jar not available in the maven central repository, so we have to manually install it into our local maven repository.

```
wget http://www.datanucleus.org/downloads/maven2/javax/jdo/jdo2-api/2.3-ec/jdo2-api-2.3-ec.jar -O ~/jdo2-api-2.3-ec.jar
mvn install:install-file -DgroupId=javax.jdo -DartifactId=jdo2-api -Dversion=2.3-ec -Dpackaging=jar -Dfile=~/jdo2-api-2.3-ec.jar
```

### 2. mvn package 

```
cd ${project_home}
mvn clean package
```

If you want to skip unit tests, please run:
```
cd ${project_home}
mvn clean package -DskipTests
```

It will generate hive-third-functions-${version}-shaded.jar in target directory.

You can also directly download file from [release page](https://github.com/aaronshan/hive-third-functions/releases).

> current latest version is `2.0.0`

## Functions

### 1. string functions

| function| description |
|:--|:--|
|pinyin(string) -> string | convert chinese to pinyin|
|md5(string) -> string | md5 hash|
|sha256(string) -> string |sha256 hash|

### 2. array functions

| function| description |
|:--|:--|
|array_contains(array, value) -> boolean | whether ARRAY contains value or not.|

### 3. date functions

| function| description |
|:--|:--|
|day_of_week(date_string \| date) -> int | day of week,if monday,return 1, sunday return 7, error return null.|
|zodiac_en(date_string \| date) -> string | convert date to zodiac|
|zodiac_cn(date_string \| date) -> string | convert date to zodiac chinese | 
|type_of_day(date_string \| date) -> string | for chinese. 获取日期的类型(1: 法定节假日, 2: 正常周末, 3: 正常工作日 4:攒假的工作日),错误返回-1. |

### 4. JSON functions
| function| description |
|:--|:--|
|json_array_get(json, jsonPath) -> array(varchar) |returns the element at the specified index into the `json_array`. The index is zero-based.|
|json_array_length(json, jsonPath) -> array(varchar) |Returns the array length of `json` (a string containing a JSON array).|
|json_array_extract(json, jsonPath) -> array(varchar) |extract json array by given jsonPath.|
|json_array_extract_scalar(json, jsonPath) -> array(varchar) |like `json_array_extract`, but returns the result value as a string (as opposed to being encoded as JSON).|
|json_extract(json, jsonPath) -> array(varchar) |extract json by given jsonPath.|
|json_extract_scalar(json, jsonPath) -> array(varchar) |like `json_extract`, but returns the result value as a string (as opposed to being encoded as JSON).|
|json_size(json, jsonPath) -> array(varchar) |like `json_extract`, but returns the size of the value. For objects or arrays, the size is the number of members, and the size of a scalar value is zero.|

### 5. China Id Card functions

| function| description |
|:--|:--|
|id_card_province(string) -> string |get user's province|
|id_card_city(string) -> string |get user's city|
|id_card_area(string) -> string |get user's area|
|id_card_birthday(string) -> string |get user's birthday|
|id_card_gender(string) -> string |get user's gender|
|is_valid_id_card(string) -> boolean |determine is valid china id card No.|
|id_card_info(string) -> json |get china id card info. include province, city, area etc.|

## Use

Put these statements into `${HOME}/.hiverc` or exec its on hive cli env.

```
add jar ${jar_location_dir}/hive-third-functions-1.0-SNAPSHOT-shaded.jar
create temporary function array_contains as 'cc.shanruifeng.functions.array.UDFArrayContains';
create temporary function day_of_week as 'cc.shanruifeng.functions.date.UDFDayOfWeek';
create temporary function type_of_day as 'cc.shanruifeng.functions.date.UDFTypeOfDay'; 
create temporary function zodiac_cn as 'cc.shanruifeng.functions.date.UDFZodiacSignCn';
create temporary function zodiac_en as 'cc.shanruifeng.functions.date.UDFZodiacSignEn';
create temporary function pinyin as 'cc.shanruifeng.functions.string.UDFChineseToPinYin';
create temporary function md5 as 'cc.shanruifeng.functions.string.UDFMd5';
create temporary function sha256 as 'cc.shanruifeng.functions.string.UDFSha256';
create temporary function json_array_get as 'cc.shanruifeng.functions.json.UDFJsonArrayGet';
create temporary function json_array_length as 'cc.shanruifeng.functions.json.UDFJsonArrayLength';
create temporary function json_array_extract as 'cc.shanruifeng.functions.json.UDFJsonArrayExtract';
create temporary function json_array_extract_scalar as 'cc.shanruifeng.functions.json.UDFJsonArrayExtractScalar';
create temporary function json_extract as 'cc.shanruifeng.functions.json.UDFJsonExtract';
create temporary function json_extract_scalar as 'cc.shanruifeng.functions.json.UDFJsonExtractScalar';
create temporary function json_size as 'cc.shanruifeng.functions.json.UDFJsonSize';
create temporary function id_card_province as 'cc.shanruifeng.functions.card.UDFChinaIdCardProvince';
create temporary function id_card_city as 'cc.shanruifeng.functions.card.UDFChinaIdCardCity';
create temporary function id_card_area as 'cc.shanruifeng.functions.card.UDFChinaIdCardArea';
create temporary function id_card_birthday as 'cc.shanruifeng.functions.card.UDFChinaIdCardBirthday';
create temporary function id_card_gender as 'cc.shanruifeng.functions.card.UDFChinaIdCardGender';
create temporary function is_valid_id_card as 'cc.shanruifeng.functions.card.UDFChinaIdCardValid';
create temporary function id_card_info as 'cc.shanruifeng.functions.card.UDFChinaIdCardInfo';
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

### example
```
 select pinyin('中国') => zhongguo
 select md5('aaronshan') => 95686bc0483262afe170b550dd4544d1
 select sha256('aaronshan') => d16bb375433ad383169f911afdf45e209eabfcf047ba1faebdd8f6a0b39e0a32
```

```
select day_of_week('2016-07-12') => 2
select type_of_day('2016-10-01') => 1
select type_of_day('2016-07-16') => 2
select type_of_day('2016-07-15') => 3
select type_of_day('2016-09-18') => 4
select zodiac_cn('1989-01-08') => 魔羯座
select zodiac_en('1989-01-08') => Capricorn
```

```
select id_card_info('110101198901084517') => {"valid":true,"area":"东城区","province":"北京市","gender":"男","city":"北京市"}
```

```
select json_array_get("[{\"a\":{\"b\":\"13\"}}, {\"a\":{\"b\":\"18\"}}, {\"a\":{\"b\":\"12\"}}]", 1); => {"a":{"b":"18"}}
select json_array_get('["a", "b", "c"]', 0); => a
select json_array_get('["a", "b", "c"]', 1); => b
select json_array_get('["c", "b", "a"]', -1); => a
select json_array_get('["c", "b", "a"]', -2); => b
select json_array_get('[]', 0); => null
select json_array_get('["a", "b", "c"]', 10); => null
select json_array_get('["c", "b", "a"]', -10); => null
select json_array_length("[{\"a\":{\"b\":\"13\"}}, {\"a\":{\"b\":\"18\"}}, {\"a\":{\"b\":\"12\"}}]"); => 3
select json_array_extract("[{\"a\":{\"b\":\"13\"}}, {\"a\":{\"b\":\"18\"}}, {\"a\":{\"b\":\"12\"}}]", "$.a.b"); => ["\"13\"","\"18\"","\"12\""]
select json_array_extract_scalar("[{\"a\":{\"b\":\"13\"}}, {\"a\":{\"b\":\"18\"}}, {\"a\":{\"b\":\"12\"}}]", "$.a.b") => ["13","18","12"]
select json_extract("{\"a\":{\"b\":\"12\"}}", "$.a.b"); => "12"
select json_extract_scalar("{\"a\":{\"b\":\"12\"}}", "$.a.b") => 12
select json_extract_scalar('[1, 2, 3]', '$[2]');
select json_extract_scalar(json, '$.store.book[0].author');
select json_size('{"x": {"a": 1, "b": 2}}', '$.x'); => 2
select json_size('{"x": [1, 2, 3]}', '$.x'); => 3
select json_size('{"x": {"a": 1, "b": 2}}', '$.x.a'); => 0
```
