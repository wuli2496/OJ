#ifndef ENDPOINT_H
#define ENDPOINT_H

#if _WIN32 || WIN32
#include <WinSock2.h>
#include <ws2ipdef.h>
#else
#include <arpa/inet.h>
#endif

#include <iostream>
#include "namespace.h"

BEGIN_NAMESPACE

class EndPoint
{
public:
    EndPoint();

    EndPoint(int family, unsigned short port);

    sockaddr* data();

    std::size_t size();

    bool isV4()
    {
        return data_.base.sa_family == AF_INET;
    }
private:
    union data_union
    {
        sockaddr base;
        sockaddr_in v4;
        sockaddr_in6 v6;
    } data_;
};

END_NAMESPACE

#endif // ENDPOINT_H
