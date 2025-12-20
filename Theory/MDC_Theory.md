**What is MDC in Spring?**



&nbsp;	MDC (Mapped Diagnostic Context) is a logging feature provided by SLF4J.

&nbsp;	It allows you to store extra information (key–value pairs) for each request and automatically include it in log messages.

👉 In simple words:

&nbsp;	MDC helps you track “who did what request” in your logs.



**Why MDC is Important in Spring Applications:** 



&nbsp;  **In a Spring application:**

&nbsp;	Many users send requests at the same time

&nbsp;	Logs from different requests mix together

&nbsp;	It becomes hard to understand which log belongs to which request



&nbsp;  **MDC solves this problem by attaching data like:**

&nbsp;	HTTP method (GET, POST)

&nbsp;	User information (name, email)

&nbsp;	Correlation ID (unique request ID)

&nbsp;  So every log line becomes clear, traceable, and readable



**5. Structure of MDC**

&nbsp;	**Step	Action							Example**

&nbsp;	Put	Add key-value to MDC					MDC.put("userId", "U567")

&nbsp;	Get	Retrieve value						MDC.get("userId")

&nbsp;	Log	Logging framework automatically appends MDC values	INFO: User login \[userId=U567]

&nbsp;	Clear	Remove values after request				MDC.clear()

