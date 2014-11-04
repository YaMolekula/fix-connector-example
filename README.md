# FIX connector example

This code is meant as a reference when implementing a connector to Paymium's FIX API.

When run it will simply connect to the acceptor, maintain a session, and subscribe to market data updates.

The first reply from the API will include a full market data snapshot, subsequent updates will be streamed as they happen.

For questions regarding this example, or the FIX interface, please contact the Paymium support by opening a ticket at paymium.com
