tyrus-test
==========

Sample testcase of tyrus issues

Deploy tyrus-test on WildFly 8.1.0.Final

Start tyrus-test-client OneEndpoint or TwoEndpoints. Both are transmiting integers (OneEndpoints is starting two connections to one server endpoint, and TwoEndpoints are connecting to two separate server endpoints). 
The server retransmits the integer to other connected clients - but only one of the client gets the data (both are sending!).

---

Sample log:
TwoEndpoints

    cze 16, 2014 4:08:50 PM pl.cmil.tyrus.TwoEndpoints$Client open
    INFO: Channel oppened 19e2d086-e5c0-4874-a027-c205ed958bfa
    cze 16, 2014 4:08:50 PM pl.cmil.tyrus.TwoEndpoints$Client open
    INFO: Channel oppened a6f6cd81-b0df-4a25-8664-92bdcf9d7da9
    cze 16, 2014 4:08:52 PM pl.cmil.tyrus.TwoEndpoints$Client message
    INFO: 19e2d086-e5c0-4874-a027-c205ed958bfa received 5JOccVF-7sMnKwDC0kc9xfs_;1
    cze 16, 2014 4:08:56 PM pl.cmil.tyrus.TwoEndpoints$Client message
    INFO: 19e2d086-e5c0-4874-a027-c205ed958bfa received 5JOccVF-7sMnKwDC0kc9xfs_;2
    cze 16, 2014 4:09:00 PM pl.cmil.tyrus.TwoEndpoints$Client message
    INFO: 19e2d086-e5c0-4874-a027-c205ed958bfa received 5JOccVF-7sMnKwDC0kc9xfs_;3
    cze 16, 2014 4:09:04 PM pl.cmil.tyrus.TwoEndpoints$Client message
    INFO: 19e2d086-e5c0-4874-a027-c205ed958bfa received 5JOccVF-7sMnKwDC0kc9xfs_;4

Server:

    16:08:50,521 INFO  [Endpoint] (default task-26) New connection opened 5JOccVF-7sMnKwDC0kc9xfs_
    16:08:50,583 INFO  [Endpoint2] (default task-25) New connection opened acSkEQQ5O7FmVGDUNQ6d2_p_
    16:08:52,594 INFO  [Endpoint] (default task-28) Sending update to 5JOccVF-7sMnKwDC0kc9xfs_
    16:08:52,594 INFO  [Endpoint] (default task-28) Sent: 5JOccVF-7sMnKwDC0kc9xfs_;1
    16:08:54,595 INFO  [Endpoint2] (default task-29) Sending update to acSkEQQ5O7FmVGDUNQ6d2_p_
    16:08:54,595 INFO  [Endpoint2] (default task-29) Sent: acSkEQQ5O7FmVGDUNQ6d2_p_;1
    16:08:56,594 INFO  [Endpoint] (default task-30) Sending update to 5JOccVF-7sMnKwDC0kc9xfs_
    16:08:56,595 INFO  [Endpoint] (default task-30) Sent: 5JOccVF-7sMnKwDC0kc9xfs_;2
    16:08:58,594 INFO  [Endpoint2] (default task-10) Sending update to acSkEQQ5O7FmVGDUNQ6d2_p_
    16:08:58,595 INFO  [Endpoint2] (default task-10) Sent: acSkEQQ5O7FmVGDUNQ6d2_p_;2
    16:09:00,595 INFO  [Endpoint] (default task-33) Sending update to 5JOccVF-7sMnKwDC0kc9xfs_
    16:09:00,596 INFO  [Endpoint] (default task-33) Sent: 5JOccVF-7sMnKwDC0kc9xfs_;3
    16:09:02,595 INFO  [Endpoint2] (default task-32) Sending update to acSkEQQ5O7FmVGDUNQ6d2_p_
    16:09:02,596 INFO  [Endpoint2] (default task-32) Sent: acSkEQQ5O7FmVGDUNQ6d2_p_;3
    16:09:04,595 INFO  [Endpoint] (default task-34) Sending update to 5JOccVF-7sMnKwDC0kc9xfs_
    16:09:04,596 INFO  [Endpoint] (default task-34) Sent: 5JOccVF-7sMnKwDC0kc9xfs_;4
    16:09:08,596 INFO  [Endpoint] (default task-36) Sending update to 5JOccVF-7sMnKwDC0kc9xfs_
    16:09:10,597 INFO  [Endpoint2] (default task-31) Sending update to acSkEQQ5O7FmVGDUNQ6d2_p_
    16:09:10,598 INFO  [Endpoint2] (default task-31) Sent: acSkEQQ5O7FmVGDUNQ6d2_p_;4


Unfortunately, the behaviour is undeterministic. Sometimes it works.

Tested on Windows 7, JDK 7.

