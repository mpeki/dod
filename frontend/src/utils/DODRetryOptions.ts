import retry, { OperationOptions, RetryOperation } from "retry";

export const operation: RetryOperation = retry.operation({
  retries: 5,
  factor: 2,
  minTimeout: 3000, // 5 seconds
  maxTimeout: Infinity
});

export const options: OperationOptions = {
  retries: 5,
  factor: 2,
  minTimeout: 3000, // 5 seconds
  maxTimeout: Infinity
};