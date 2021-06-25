package com.cyn.commons.exception;

/** Auth error codes. */
public enum ErrorCode {

  /** Failed to authenticate. */
  ATH_ERR000001,

  /** Invalid access token. */
  ATH_ERR000002,

  /** User is not active. */
  ATH_ERR000003,

  /** Access Denied. */
  ATH_ERR000004,

  /** Cannot get access token for service to service call. */
  ATH_ERR000005,

  /** Cannot authorize request body. */
  ATH_ERR000006,

  /** Cannot decrypt a request. */
  DEC_ERR000001,

  /** Cannot decrypt db data. */
  DEC_ERR000002,

  /** Cannot encrypt a request. */
  ENC_ERR000001,

  /** Cannot encrypt db data. */
  ENC_ERR000002,

  /** Key exchange communication issue. */
  KEY_ERR000001,

  /** Template processing error. */
  TMP_ERR000001,

  /** Internal secret encrypt error. */
  CPT_ERR000001,

  /** Internal secret decrypt error. */
  CPT_ERR000002,

  /** Internal secret decrypt app guid error. */
  CPT_ERR000003,

  /** Internal secret generate Guid error. */
  CPT_ERR000004,

  /** No secret key error. */
  CPT_ERR000005,
}
