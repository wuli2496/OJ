#ifndef ENDPOINT_H
#define ENDPOINT_H

#if _WIN32 || WIN32
#include <WinSock2.h>
#include <ws2ipdef.h>
#else
#include <arpa/inet.h>
#endif


#include "namespace.h"

BEGIN_NAMESPACE

class EndPoint
{
public:
    EndPoint();

private:
    union data_union
    {
        sockaddr base;
        sockaddr_in v4;
        sockaddr_in6 v6;
    }data_;
};

END_NAMESPACE

#endif // ENDPOINT_H
