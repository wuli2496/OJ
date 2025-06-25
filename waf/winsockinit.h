#ifndef WINSOCKINIT_H
#define WINSOCKINIT_H

#if defined(WIN32)

class WinsockInitBase
{
protected:
    struct data
    {
        long initCount;
        long result;
    };
    static void startup(data& d, unsigned char major, unsigned char minor);

    static void cleanup(data& d);
};

template <int Major = 2, int Minor = 2>
class WinsockInit : private WinsockInitBase
{
public:
    WinsockInit()
    {
        startup(data_, Major, Minor);
    }

    WinsockInit(const WinsockInit&)
    {
        startup(data_, Major, Minor);
    }

    ~WinsockInit()
    {
        cleanup(data_);
    }

private:
    static data data_;
};

template<int Major, int Minor>
WinsockInitBase::data WinsockInit<Major, Minor>::data_;

static const WinsockInit<>& winsockInitInstance = WinsockInit<>();
#endif
#endif // WINSOCKINIT_H
