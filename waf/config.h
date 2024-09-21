#ifndef CONFIG_H
#define CONFIG_H

#include "namespace.h"

#if _WIN32 || WIN32
#include <WinSock2.h>
#endif

BEGIN_NAMESPACE

#if defined(_WIN32) || defined(WIN32)
typedef HANDLE WafHandle;
typedef SOCKET WafSocket;
#define INVALID_HANDLE INVALID_HANDLE_VALUE
#else
typedef int WafHandle;
typedef int WafSocket;
#define INVALID_HANDLE -1
#endif

END_NAMESPACE

#endif // CONFIG_H
