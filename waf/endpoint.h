#ifndef ENDPOINT_H
#define ENDPOINT_H

#if defined(WIN32)
#include <WinSock2.h>
#include <ws2ipdef.h>
#include <ws2tcpip.h>
#else
#include <arpa/inet.h>
#endif

#include <string>
#include <iostream>
#include "namespace.h"

BEGIN_NAMESPACE

class LIBRARY_API EndPoint
{
public:
    EndPoint();

    EndPoint(std::string ip, unsigned short port);

    const sockaddr* data() const;

    std::size_t size() const;

    inline bool isV4() const
    {
        return data_.base.sa_family == AF_INET;
    }

    std::string getIp();

    unsigned short getPort();

private:
    union data_union
    {
        sockaddr base;
        sockaddr_in v4;
        sockaddr_in6 v6;
    } data_;

    const static int MAX_LEN = 256;
};

END_NAMESPACE

#endif // ENDPOINT_H
