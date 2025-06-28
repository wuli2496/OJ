#include "endpoint.h"

#include "socketops.h"

BEGIN_NAMESPACE(waf)

EndPoint::EndPoint()
{
    v4.sin_family = AF_INET;
    v4.sin_port = 0;
    v4.sin_addr.s_addr = SocketOps::hostToNetworkLong(INADDR_ANY);
}

EndPoint::EndPoint(std::string ip, unsigned short port)
{

    v4.sin_family = AF_INET;
    v4.sin_port = htons(port);
    v4.sin_addr.s_addr = htonl(INADDR_ANY);
    SocketOps::inet_pton(AF_INET, ip.c_str(), &(v4.sin_addr.s_addr));
}

const sockaddr* EndPoint::data() const
{
    return &base;
}

sockaddr* EndPoint::data()
{
    return &base;
}

std::size_t EndPoint::size() const
{
    if (isV4()) {
        return sizeof(v4);
    }

    return sizeof(v6);
}

std::string EndPoint::getIp()
{
    char addr[MAX_LEN];
    const char* tmp = SocketOps::inet_ntop(AF_INET, &(v4.sin_addr.s_addr), addr, MAX_LEN);

    if (tmp == nullptr)
    {
        return "";
    }


    return addr;
}

unsigned short EndPoint::getPort()
{
    return SocketOps::networkToHostShort(v4.sin_port);
}


END_NAMESPACE(waf)
