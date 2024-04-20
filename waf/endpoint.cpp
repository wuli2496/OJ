#include "endpoint.h"


BEGIN_NAMESPACE

EndPoint::EndPoint()
    :data_()
{
    data_.v4.sin_family = AF_INET;
    data_.v4.sin_port = 0;
    data_.v4.sin_addr.s_addr = INADDR_ANY;
}

EndPoint::EndPoint(int family, unsigned short port)
    :data_()
{
    if (family == AF_INET)
    {
        data_.v4.sin_family = AF_INET;
        data_.v4.sin_port = htons(port);
        data_.v4.sin_addr.s_addr = INADDR_ANY;
    }
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
