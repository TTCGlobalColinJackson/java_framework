# java_framework
## **Template Java-Selenium Framework**

This template project incorporates several features comprising a basic working Java-Selenium test framework.

    JUnit - for unit testing

    Maven - for project management

    Java - base language

    Cucumber - for test scenario specification
 
    Selenium - for GUI-based automation

    Cucumber reports - for reporting

    log4j - for logging

A separate project will add Selenide for Selenide-based frameworks (raw Selenium and Selenide configurations cannot easily co-exist in the same project).

**Note:** This project has been created in _IntelliJ_ due to its increased contextual help and the quality of the Cucumber plugin. Projects created in IntelliJ are not straightforwardly compatible with Eclipse. to create an Eclipse version of the project, load it into IntelliJ and select "File">"Export project to eclipse". better still, switch to IntelliJ! 

### **Architecture**

This framework uses a feature-step-controller-model-view paradigm, with each class type representing one (and only one) of these five archetypes.

**_Feature Files_** - Feature Files are Cucumber files (so not class files, strictly speaking) that define the test scenarios used to exercise the system. they express business requirements, and should never refer to the technical details of how the tests are run, or even reflect business logic to any great degree.

**_Step Classes_** - these contain the business logic of the tests defined by the Cucumber scenarios. Each step in the Cucumber scenario is represented by one step method. Generally, step methods are grouped together in Step Definition files representing their business function (e.g. "login", "payment processing"), reflective of the feature areas of the scenarios that require them. However, many of the step definitions will be generic and used in multiple scenarios (for example, "GIVEN that I am logged in as a subscribed user"). for this reason, the scenarios in a Feature File will mostly point to a single Step Definition file containing step methods for the steps in the Feature File, but some steps may point to step methods in other Step Definition files. The Cucumber plugin for IntelliJ manages most of this for you in an intuitive manner.

Step methods will not access the GUI or other application components directly (via Selenium). For accessing such functionality, they will call a controller, helper or view method to fetch the target object, depending on the circumstances. They can directly interact with the GUI component as an object, rather than as a WebElement. Step methods should generally be quite linear and script-like, representing the actions a manual tester might well make.
  
**_Controller Classes_** - a controller (or helper) class is used to abstract away complex functionality associated with an action (anything beyond _set_ and _get_, generally speaking). in this project I've separated out "controllers" and "helpers", with "helpers" being oriented towards very generic actions (such as, for example, API calls, browser control methods, etc) and "controllers" being more oriented to the specific functionality of the application under test (e.g. login controller, date-picker controller). The controllers cover an aspect of the functional operation of the application and add context where required. They usually will not access GUI components directly through selenium/selenide, as this capability is abstracted away into the "view" classes.  controllers may call other controllers, helpers and view classes.

**_View Classes_** - view class (also known as page objects) contain the nuts and bolts selenium/selenide code for interacting with the GUI. A view class will usually contain all the selenium/selenide required to interact with a single page. Each distinct page has its own view file. an ideal view class just contains selenium/selenide object getters and nothing else, as any complex logic is devolved to a controller class associated with the page. 
Small snippets of logic are occasionally required, simply to handle the target GUI objects. However, view methods should be _context free_, not making any assumptions about the test's intentions, where the method is called from, or what the state of the application under test is in. They are, to as great an extent as possible, simple interfaces to the objects in the GUI.

The view page does not generally have code specifically to "set" GUI objects, as this is done by the calling method. So, for example, a step definition or controller method may use the view method to "fetch" the GUI object, then interact with the object as required (set some text, click on it, check an attribute). By this means, we abstract away all the selenium, and let our calling methods treat GUI elements as objects.   

**_Model Classes_** - the model is a thread safe object used to store a running model of the scenario in progress. As it contains the record of the test constructed on-the-fly, it obviates the need for context inject. I have not included any other context injection in this project. It is a global model, and therefore can be accessed across the steps in a scenario, so is used also as a means of passing data between steps in the test.

Using a model instead of context injections means that you always have access to the complete 'expected state' of the test, and can cross-reference, for example, data you input at the start of the test with output generated at the end of the test. It is in effect a more flexible and sophisticated context injection methodology.

However, using a model will sometimes incur some development overheads, as you have to build model objects representing the test to contain the information you wish to store, in a properly object-oriented manner.

For example, consider how we might store information about a user's credit card details as we move through a test designed to exercise this feature of the application under test:

    model > user > credit card

The model is our global object, and anything created inside the model is available throughout the test. Our model object might have a _List<user>_ containing some user objects. I have also created a user object in the model section, but it's capabilities would likely need to be enhanced (it currently just has a username and password).

In fact, for the above example, there would need to be a credit card object, and our User object would need to be able to hold one or more instances of Credit Card.

In this manner, we build up our collection of model objects, and they can be re-used and expanded as required.

### **Cucumber Reports**

The standard Cucumber reports output is enhanced by features provided by Monochromata. The start HTML file is "overview-features.html" in the directory "target/cucumber-html-reports". 

This reporting tool gives summaries of the test result by feature, by tag, by step and by failure. 

It can be lightly configured by the configuration options in the properties file "main/resources/prettyreports.properties"

### **log4j**

The logging methodology uses log4j, which can be configured in the property file "main/resources/log4j.properties"

