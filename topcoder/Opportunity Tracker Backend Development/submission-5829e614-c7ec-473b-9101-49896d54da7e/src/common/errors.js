/**
 * This file defines application errors
 */
const util = require('util')

/**
 * Helper function to create generic error object with http status code
 * @param name the error name
 * @param statusCode the http status code
 * @returns the error constructor
 */
function createError (name, statusCode) {
  /**
   * The error constructor
   * @param message the error message
   * @param [cause] the error cause
   * @constructor
   */
  function ErrorCtor (message, cause) {
    Error.call(this)
    Error.captureStackTrace(this)
    this.message = message || name
    this.cause = cause
    this.httpStatus = statusCode
  }

  util.inherits(ErrorCtor, Error)
  ErrorCtor.prototype.name = name
  return ErrorCtor
}

module.exports = {
  BadRequestError: createError('BadRequestError', 400),
  UnauthorizedError: createError('UnauthorizedError', 401),
  NotFoundError: createError('NotFoundError', 404),
  InternalServerError: createError('InternalServerError', 500)
}
