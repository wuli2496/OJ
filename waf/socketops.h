#ifndef SOCKETOPS_H
#define SOCKETOPS_H

#include "export.h"
#include "platform.h"


BEGIN_NAMESPACE(waf)
BEGIN_NAMESPACE(SocketOps)

WafSocket socket(int af, int type, int protocol);

bool setNonBlocking(WafSocket s, bool value);

int bind(WafSocket s, sockaddr* addr, int addrlen);

int listen(WafSocket s, int backlog);

int accept(WafSocket s, sockaddr* addr, int* addrlen);

int connect(WafSocket s, sockaddr* addr, int addrlen);

int getSocketError();

const char* inetNtop(int af, const void* src, char* dest, size_t length);

int inetPton(int af, const char* src, void* dest);

unsigned short networkToHostShort(unsigned short value);

unsigned short hostToNetworkShort(unsigned short value);

unsigned long networkToHostLong(unsigned long value);

unsigned long hostToNetworkLong(unsigned long value);

int getSockOpt(WafSocket s, int level, int optname, char *optval, int *optlen);

int setSockOpt(WafSocket s, int level, int optname, char *optval, int optlen);

END_NAMESPACE(SocketOps)
END_NAMESPACE(waf)

#endif // SOCKETOPS_H
