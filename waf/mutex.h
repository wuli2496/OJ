#ifndef MUTEX_H
#define MUTEX_H

#include "platform.h"
#include "export.h"

BEGIN_NAMESPACE(waf)

#ifdef OS_WIN
typedef CRITICAL_SECTION wafmutex ;
typedef SRWLOCK wafrwlock ;
typedef HANDLE wafsem;
typedef CONDITION_VARIABLE wafcond;
#else
typedef pthread_mutex_t wafmutex;
typedef pthread_rwlock_t wafwlock ;
typedef sem_t wafsem;
typedef pthread_cond_t wafcond;
#endif

END_NAMESPACE(waf)

#endif // MUTEX_H
