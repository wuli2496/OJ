#ifndef BASICSOCKET_H
#define BASICSOCKET_H

#include "config.h"
#include "namespace.h"

BEGIN_NAMESPACE

class BasicSocket
{
public:
    BasicSocket();

    WafHandle getHandle();

    void setHandle(WafHandle handle);

    int open(int type, int protocol_family, int protocol, int resuse_addr);

    int getOption(int level, int option, void* optval, int* optlen);

    int setOption(int level, int option, void* optval, int optlen);

    int close();

private:
    WafHandle handle;
};

END_NAMESPACE

#endif // BASICSOCKET_H
