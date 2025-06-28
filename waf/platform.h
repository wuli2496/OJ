#ifndef PLATFORM_H
#define PLATFORM_H

#if defined(WIN32) || defined(_WIN32)
    #define OS_WIN32
#elif defined(WIN64) || defined(_WIN64)
    #define OS_WIN64
    #define OS_WIN32
#endif

#if defined(OS_WIN32) || defined(OS_WIN64)
    #define OS_WIN
#endif


#ifdef OS_WIN
    #include <WinSock2.h>
    #include <ws2ipdef.h>
    #include <ws2tcpip.h>
    #include <windows.h>

    typedef HANDLE WafHandle;
    typedef SOCKET WafSocket;
    #define INVALID_HANDLE INVALID_HANDLE_VALUE
    #define INVALID_SOCKET_VALUE INVALID_SOCKET
#else
    #include <arpa/inet.h>
    #include <sys/types.h>
    #include <sys/socket.h>
    #include <unistd.h>

    typedef int WafHandle;
    typedef int WafSocket;
    #define INVALID_HANDLE -1
    #define INVALID_SOCKET_VALUE -1
#endif

#endif // PLATFORM_H
