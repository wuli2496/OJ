#include "basicsocket.h"

#if defined(_WIN32) || defined(WIN32)
#else
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#endif

BEGIN_NAMESPACE

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
    return ::getsockopt((WafSocket)getHandle(), level, option, (char*)optval, (socklen_t*)optlen);
}

int BasicSocket::setOption(int level, int option, void* optval, std::size_t optlen)
{
    return ::setsockopt((WafSocket)getHandle(), level, option, (char*)optval, optlen);
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
#if defined(_WIN32) || defined(WIN32)
        result = ::closesocket((WafSocket)getHandle());
#else
        result = ::close(getHandle());
#endif
    }

    return result;
}


END_NAMESPACE