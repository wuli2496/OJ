#ifndef SOCKETOPS_H
#define SOCKETOPS_H

#include "export.h"
#include "platform.h"


BEGIN_NAMESPACE(waf)
BEGIN_NAMESPACE(SocketOps)

WafSocket socket(int af, int type, int protocol);

bool setNonBlocking(WafSocket s, bool value);

int getSocketError();

const char* inet_ntop(int af, const void* src, char* dest, size_t length);

int inet_pton(int af, const char* src, void* dest);

unsigned short networkToHostShort(unsigned short value);

unsigned short hostToNetworkShort(unsigned short value);

unsigned long networkToHostLong(unsigned long value);

unsigned long hostToNetworkLong(unsigned long value);

END_NAMESPACE(SocketOps)
END_NAMESPACE(waf)

#endif // SOCKETOPS_H
