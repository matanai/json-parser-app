### Java task

By using the Java programming language, implement a program that is able to filter person 
objects from a JSON file based on their fields and present the result to the user. The 
program is also able to sort the result in ascending or descending order using one of the 
fields as a key. It also declares the number of filtered objects.

The JSON objects are contained in an JSON array and structured as follows:
```json
{
    "firstName": "Donald", 
    "lastName": "Duck", 
    "age": 86,
    "gender": "male"
}
```
The program should take into account the cases if the JSON file is missing, incorrectly 
formatted or does not follow the schema described above. If the file contains objects that do 
not follow the correct schema, but is otherwise as required, the program can skip those 
particular objects and continue normally.You can use input.json to test the valid case and 
invalid_format.json and invalid_schema.json for invalid cases.

The filtering is performed by filtering out all the objects which do not match the filter criteria, 
which compares a single field to a given value. The allowed comparison operators for the 
age field are =, >=, <=, > and <, and for the other fields, only equality is checked. You are not 
required to implement any logical operators (and, or, not).

For more accurate specifications, you can check the example runs in example_runs.txt, 
which describes in detail how the program should work. The program should be run from the 
command line and prompt the user for answers.

For JSON parsing, you can use for example org.json or gson libraries. 

Other evaluation criteria:
* Design
* Structure
* Use of language features
* Error checking
* Code style and readability
* Comments (where actually needed)
* Testability
* Documentation
* Usability

A proper usage of link: [Enum Types](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html), [Optional](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Optional.html), [Switch Expressions](https://www.wearedevelopers.com/magazine/modern-java-switch-expressions) and [Streams](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/Stream.html) are appreciated 
when evaluating the design and code style of your solution. Unit tests, when demonstrated 
correctly, are a big plus but not required.
