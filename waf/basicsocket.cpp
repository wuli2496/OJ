#include "basicsocket.h"

#include "platform.h"
#include "socketops.h"

BEGIN_NAMESPACE(waf)

BasicSocket::BasicSocket()
{

}


WafHandle BasicSocket::getHandle()
{
    return handle;
}

void BasicSocket::setHandle(WafHandle handle)
{
    this->handle = handle;
}

int BasicSocket::getOption(int level, int option, void* optval, std::size_t* optlen)
{
    return SocketOps::getSockOpt((WafSocket)getHandle(), level, option, (char*)optval, (int*)optlen);
}

int BasicSocket::setOption(int level, int option, void* optval, std::size_t optlen)
{
    return SocketOps::setSockOpt((WafSocket)getHandle(), level, option, (char*)optval, optlen);
}

int BasicSocket::open(int type, int protocol_family, int protocol, int resuse_addr)
{
    setHandle((WafHandle)socket(protocol_family, type, protocol));
    if (getHandle() == INVALID_HANDLE) {
        return -1;
    }

    int one = 1;
    if (resuse_addr && setOption(SOL_SOCKET, SO_REUSEADDR, &one, sizeof one) == -1) {
        close();
        return -1;
    }

    return 0;
}

int BasicSocket::close()
{
    int result = 0;
    if (getHandle() != INVALID_HANDLE) {
#if defined(OS_WIN)
        result = ::closesocket((WafSocket)getHandle());
#else
        result = ::close(getHandle());
#endif
    }

    return result;
}

int BasicSocket::bindAddress(const EndPoint& endpoint)
{
    int ret = SocketOps::bind((WafSocket)handle, (struct sockaddr*)(endpoint.data()), endpoint.size());

    return ret;
}

int BasicSocket::listen()
{
    int ret = SocketOps::listen((WafSocket)handle, SOMAXCONN);
    return ret;
}

int BasicSocket::accept(EndPoint& endpoint)
{
    int len = 0;
    int fd = SocketOps::accept((WafSocket)handle, endpoint.data(), &len);

    return fd;
}

END_NAMESPACE(waf)
