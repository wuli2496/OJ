#include "winsockinit.h"
#include <windows.h>

#if defined(WIN32)

void WinsockInitBase::startup(data& d, unsigned char major, unsigned char minor)
{
    if (::InterlockedIncrement(&d.initCount) == 1)
    {
        WSADATA wsaData;
        long result = ::WSAStartup(MAKEWORD(major, minor), &wsaData);
        ::InterlockedExchange(&d.result, result);

    }
}

void WinsockInitBase::cleanup(data& d)
{
    if (::_InterlockedDecrement(&d.initCount) == 0)
    {
        ::WSACleanup();
    }
}

#endif
