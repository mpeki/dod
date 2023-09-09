import retry, { OperationOptions, RetryOperation } from "retry";

const config: OperationOptions = {
  retries: 5,
  factor: 2,
  minTimeout: 3000, // 5 seconds
  maxTimeout: Infinity
};

export const operation: RetryOperation = retry.operation(config);
export const options: OperationOptions = { ...config };
