import retry, { OperationOptions, RetryOperation } from "retry";

const config: OperationOptions = {
  retries: 10,
  factor: 1,
  minTimeout: 5000,
  maxTimeout: Infinity
};

export const operation: RetryOperation = retry.operation(config);
export const options: OperationOptions = { ...config };
