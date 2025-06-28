#ifndef EXPORT_H
#define EXPORT_H

#if defined(_MSC_VER)
    #if defined(EXPORTS)
        #define EXPORT __declspec(dllexport)
    #else
        #define EXPORT __declspec(dllimport)
    #endif
#elif defined(__GNUC__)
    #define EXPORT __attribute__((visibility("default")))
#else
    #define EXPORT
#endif


#ifdef __cplusplus

#ifndef EXTERN_C
#define EXTERN_C extern "C"
#endif

#ifndef BEGIN_EXTERN_C
#define BEGIN_EXTERN_C extern "C" {
#endif

#ifndef END_EXTERN_C
#define END_EXTERN_C }
#endif


#ifndef BEGIN_NAMESPACE
#define BEGIN_NAMESPACE(ns) namespace ns {
#endif

#ifndef END_NAMESPACE
#define END_NAMESPACE(ns) }
#endif

#else

#define EXTERN_C extern
#define BEGIN_EXTERN_C
#define END_EXTERN_C

#define BEGIN_NAMESPACE(ns)
#define END_NAMESPACE(ns)

#endif

#endif // EXPORT_H
