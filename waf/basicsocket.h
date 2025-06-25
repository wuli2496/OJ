#ifndef BASICSOCKET_H
#define BASICSOCKET_H

#include "config.h"
#include "namespace.h"
#include <cstddef>
#include "endpoint.h"

BEGIN_NAMESPACE

class LIBRARY_API BasicSocket
{
public:
    BasicSocket();

    WafHandle getHandle();

    void setHandle(WafHandle handle);

    int open(int type, int protocol_family, int protocol, int resuse_addr);

    int getOption(int level, int option, void* optval, std::size_t* optlen);

    int setOption(int level, int option, void* optval, std::size_t optlen);

    int close();

    int bindAddress(const EndPoint& endpoint);

    int listen();

private:
    WafHandle handle;
};

END_NAMESPACE

#endif // BASICSOCKET_H
