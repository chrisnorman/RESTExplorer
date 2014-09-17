#RESTExplorer


RESTExplorer is a simple Android application for making REST Get calls, which I mostly use as a REST API test app. It
has a simple UI for collecting and endpoint URL, verb, and parameters, and displays results returned from
the call.

- Currently doesn't handle redirects or auth
- Only handles "get" requests
- Has a minimum platform requirement of Android 4.2 (not because it really needs something from that version,
but just because I wanted to minimized the support jar dependencies).
- Has a dependency on the code in the AndroidSharedServices repo.

