#ifndef BASICSOCKET_H
#define BASICSOCKET_H

#include "config.h"
#include "namespace.h"

BEGIN_NAMESPACE

class BasicSocket
{
public:
    BasicSocket();

    WAF_HANDLE getHandle();

    void setHandle(WAF_HANDLE handle);

    int open(int type, int protocol_family, int protocol, int resuse_addr);

    int getOption(int level, int option, void* optval, int* optlen);

    int setOption(int level, int option, void* optval, int optlen);

    int close();

private:
    WAF_HANDLE handle;
};

END_NAMESPACE

#endif // BASICSOCKET_H
