#include "socketops.h"

#include "platform.h"

BEGIN_NAMESPACE(waf)
BEGIN_NAMESPACE(SocketOps)

WafSocket socket(int af, int type, int protocol)
{
#ifdef OS_WIN
    WafSocket fd = ::WSASocketW(af, type, protocol, 0, 0, WSA_FLAG_OVERLAPPED);
    if (fd == INVALID_SOCKET_VALUE)
    {
        return fd;
    }
#else
    WafSocket fd = ::socket(af, type, protocol);
    if (fd == INVALID_SOCKET_VALUE)
    {
        return fd;
    }
#endif

    return fd;
}

bool setNonBlocking(WafSocket s, bool value)
{
#ifdef OS_WIN
    unsigned long v = (value ? 1 : 0);
    int result = ::ioctlsocket(s, FIONBIO, &v);
#else
    int result = ::fcntl(s, F_GETFL, 0);
    if (result >= 0)
    {
        int flag = (value ? (result & O_NONBLOCK):(result & ~O_NONBLOCK));

        result = ::fcntl(s, F_SETFL, flag);
    }
#endif

    return result >= 0;
}

int getSocketError()
{
#ifdef OS_WIN
    return ::WSAGetLastError();
#else
    return errno;
#endif
}

const char* inetNtop(int af, const void* src, char* dest, size_t length)
{
    return ::inet_ntop(af, src, dest, length);
}

int inetPton(int af, const char* src, void* dest)
{
    return ::inet_pton(af, src, dest);
}

unsigned short networkToHostShort(unsigned short value)
{
    return ::ntohs(value);
}

unsigned short hostToNetworkShort(unsigned short value)
{
    return ::htons(value);
}

unsigned long networkToHostLong(unsigned long value)
{
    return ::ntohl(value);
}

unsigned long hostToNetworkLong(unsigned long value)
{
    return ::htonl(value);
}

int getSockOpt(WafSocket s, int level, int optname, char *optval, int *optlen)
{
    return ::getsockopt(s, level, optname, optval, optlen);
}

int setSockOpt(WafSocket s, int level, int optname, char *optval, int optlen)
{
    return ::setsockopt(s, level, optname, optval, optlen);
}

int bind(WafSocket s, sockaddr* addr, int addrlen)
{
    return ::bind(s, addr, addrlen);
}

int listen(WafSocket s, int backlog)
{
    return ::listen(s, backlog);
}

int accept(WafSocket s, sockaddr* addr, int* addrlen)
{
    return ::accept(s, addr, addrlen);
}

int connect(WafSocket s, sockaddr* addr, int addrlen)
{
    return ::connect(s, addr, addrlen);
}

END_NAMESPACE(SocketOps)
END_NAMESPACE(waf)
