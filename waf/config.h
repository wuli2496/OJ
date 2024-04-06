#ifndef CONFIG_H
#define CONFIG_H

#include "namespace.h"

#if _WIN32 || WIN32
#include <WinSock2.h>
#endif

BEGIN_NAMESPACE

#if _WIN32 || WIN32
typedef HANDLE WAF_HANDLE;
typedef SOCKET WAF_SOCKET;
#define INVALID_HANDLE INVALID_HANDLE_VALUE
#else
typedef int WAF_HANDLE;
typedef WAF_HANDLE WAF_SOCKET;
#define INVALID_HANDLE -1
#endif

END_NAMESPACE

#endif // CONFIG_H
