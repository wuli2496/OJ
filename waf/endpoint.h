#ifndef ENDPOINT_H
#define ENDPOINT_H

#if _WIN32 || WIN32
#include <WinSock2.h>
#else
#include <arpa/inet.h>
#endif


#include "namespace.h"

BEGIN_NAMESPACE
typedef in_addr ip_t;

static const ip_t IP_ANY = {INADDR_ANY};

class EndPoint
{
public:
    EndPoint();

public:
    ip_t ip;
    int port;
};

END_NAMESPACE

#endif // ENDPOINT_H
