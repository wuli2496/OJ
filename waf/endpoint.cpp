#include "endpoint.h"


BEGIN_NAMESPACE

EndPoint::EndPoint()
    :data_()
{
    data_.v4.sin_family = AF_INET;
    data_.v4.sin_port = 0;
    data_.v4.sin_addr.s_addr = htonl(INADDR_ANY);
}

EndPoint::EndPoint(std::string ip, unsigned short port)
    :data_()
{

    data_.v4.sin_family = AF_INET;
    data_.v4.sin_port = htons(port);
    data_.v4.sin_addr.s_addr = htonl(INADDR_ANY);
    inet_pton(AF_INET, ip.c_str(), &(data_.v4.sin_addr.s_addr));
}

sockaddr* EndPoint::data()
{
    return &data_.base;
}

std::size_t EndPoint::size()
{
    if (isV4()) {
        return sizeof(data_.v4);
    }

    return sizeof(data_.v6);
}


END_NAMESPACE
