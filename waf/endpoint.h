#ifndef ENDPOINT_H
#define ENDPOINT_H

#include "platform.h"
#include <string>
#include <iostream>
#include "export.h"

BEGIN_NAMESPACE(waf)

class EXPORT EndPoint
{
public:
    EndPoint();

    EndPoint(std::string ip, unsigned short port);

    const sockaddr* data() const;

    sockaddr* data();

    std::size_t size() const;

    inline bool isV4() const
    {
        return base.sa_family == AF_INET;
    }

    std::string getIp();

    unsigned short getPort();

private:
    union
    {
        sockaddr base;
        sockaddr_in v4;
        sockaddr_in6 v6;
    };

    const static int MAX_LEN = 256;
};

END_NAMESPACE(waf)

#endif // ENDPOINT_H
